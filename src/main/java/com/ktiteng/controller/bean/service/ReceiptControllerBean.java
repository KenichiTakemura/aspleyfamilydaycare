package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toS;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.ktiteng.afdc.InvoiceType;
import com.ktiteng.cdi.Config;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.AfdcConfig;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.controller.bean.GmailSender;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.PdfGenerator;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.controller.service.TaxInvoiceSeederController;
import com.ktiteng.entity.manager.PersistenceManager;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;
import com.ktiteng.util.Utils;

@Default
@ApplicationScoped
public class ReceiptControllerBean extends BaseController implements ReceiptController {
	@Inject
	PdfGenerator pdfGen;

	@Inject
	@Log
	private Logger log;
	@Inject
	TaxInvoiceSeederController tcs;

	@Inject
	PersistenceManager pm;

	@Inject
	GmailSender gmailSender;

	@Inject
	ChildController cc;

	@Inject
	PaymentController pc;

	@Inject
	@Config
	AfdcConfig config;

	@Override
	public Receipt findReceipt(String id) throws IOException {
		return (Receipt) em.find(Receipt.class, id);
	}

	@Override
	public Receipt issueReceipt(String childId, Payable payable) throws IOException {
		log.info("issueReceipt childId={}, payable={}", childId, payable);
		String receiptId = payable.getReceiptId();
		if (receiptId != null) {
			if (findReceipt(receiptId) != null) {
				throw new IOException("Receipt already issued.");
			} else {
				log.info("Receipt not found with {}", receiptId);
			}
		}
		Receipt receipt = findReceipt(payable.getId());
		if (receipt != null) {
			log.info("Receipt found by {}", payable.getId());
		} else {
			Child child = findChild(childId);
			Document document = convertToDocument(child, payable);
			receipt = createReceipt(child, payable);
			pdfGen.generateReceipt(document, receipt.getLocation(), receipt.getType());
			receipt.setIssued(true);
			receipt.setDateIssued(LocalDate.now());
			receipt.setPayableId(payable.getId());
			em.save(receipt);
		}
		payable.setReceiptId(receipt.getId());
		return receipt;
	}

	@Override
	public void deleteReceipt(String childId, String receiptId) throws IOException {
		log.info("deleteReceipt childId={}, receiptId={}", childId, receiptId);
		Receipt receipt = findReceipt(receiptId);
		if (receipt == null) {
			throw new IOException("Receipt not found by " + receiptId);
		}
		if (!receipt.isIssued()) {
			throw new IllegalStateException("Receipt has not been issued.");
		}
		if (receipt.isSent()) {
			throw new IllegalStateException("Receipt has been sent, cannot deleted.");
		}
		Paths.get(receipt.getLocation()).toFile().delete();
		log.info("{} has been deleted.", receipt.getLocation());
		receipt.setIssued(false);
		receipt.setDateIssued(null);
		String payableId = receipt.getPayableId();
		receipt.setPayableId(null);
		em.save(receipt);
		if (receipt.getType() == InvoiceType.WEEKS) {
			pc.updatePaymentSchedule(childId, pc.findPaymentSchedule(childId, payableId).setReceiptId(null));
		} else if (receipt.getType() == InvoiceType.DEPOSIT) {
			pc.updateDeposit(childId, pc.findDeposit(childId).setReceiptId(null));
		} else {
			pc.updateEnrollmentFee(childId, pc.findEnrollmentFee(childId).setReceiptId(null));
		}
	}

	@Override
	public void sendReceipt(String childId, String receiptId) throws IOException {
		Child child = findChild(childId);
		Receipt receipt = findReceipt(receiptId);
		if (sendReceipt(child, receipt)) {
			receipt.setSent(true);
			em.save(receipt);
		}
	}

	boolean sendReceipt(Child child, Receipt receipt) throws IOException {
		if (!receipt.isSent()) {
			String to = cc.findParent(child.getParentId()).getEmailAddress();
			if (to == null) {
				throw new IOException("Parent Email not found for " + child.getParentId());
			}
			String cc = config.getKaAdminEmail();
			gmailSender.sendEmail(to, cc, receipt.getName(), config.getEmailContents(), receipt.getLocation());
			receipt.setSent(true);
			receipt.setDateSent(LocalDate.now());
		} else {
			log.info("Receipt was already sent");
			return false;
		}
		return true;
	}

	private Receipt createReceipt(Child child, Payable payable) throws IOException {
		Receipt receipt = new Receipt().setTaxInvoiceId(tcs.generateNextId());
		String location = null;
		if (payable instanceof PaymentSchedule) {
			PaymentSchedule paymentSchedule = (PaymentSchedule) payable;
			receipt.setName(String.format("Receipt for %s %s(%s-%s)", child.getFirstName(), child.getLastName(),
					Utils.toS(paymentSchedule.getBillingStartDate()), Utils.toS(paymentSchedule.getBillingEndDate())));
			location = String.format("%s/receipt/Receipt_%s_%s(%s-%s).pdf", pm.getPath().toString(),
					child.getFirstName(), child.getLastName().trim(),
					Utils.toS(paymentSchedule.getBillingStartDate()).replaceAll("/", ""),
					Utils.toS(paymentSchedule.getBillingEndDate()).replaceAll("/", ""));
			receipt.setType(InvoiceType.WEEKS);
		} else if (payable instanceof Deposit) {
			receipt.setName(String.format("Deposit Receipt for %s %s", child.getFirstName(), child.getLastName()));
			location = String.format("%s/receipt/Deposit_Receipt_%s_%s.pdf", pm.getPath().toString(),
					child.getFirstName(), child.getLastName().trim());
			receipt.setType(InvoiceType.DEPOSIT);
		} else if (payable instanceof EnrollmentFee) {
			receipt.setName(
					String.format("Enrollment Fee Receipt for %s %s", child.getFirstName(), child.getLastName()));
			location = String.format("%s/receipt/EnrollmentFee_Receipt_%s_%s.pdf", pm.getPath().toString(),
					child.getFirstName(), child.getLastName().trim());
			receipt.setType(InvoiceType.ENROLLMENT);
		}
		receipt.setGeneratedAt();
		receipt.setLocation(location);
		return receipt;
	}

	Document convertToDocument(Child child, Payable payable) throws IOException {
		Element root = getRootElement();
		addElement(root, "date", toS(payable.getDateReceived()));
		addElement(root, "childName", child.getName());
		addElement(root, "childNumber", child.getChildNumber());
		if (payable instanceof PaymentSchedule) {
			PaymentSchedule paymentSchedule = (PaymentSchedule) payable;
			addElement(root, "weekStart", toS(paymentSchedule.getBillingStartDate()));
			addElement(root, "weekEnd", toS(paymentSchedule.getBillingEndDate()));
		}
		addElement(root, "accountAmount", toS(payable.getAmountInvoiced()));
		addElement(root, "accountPaid", toS(payable.getAmountReceived()));
		String balanceDue = Double.compare(payable.getBalanceDue(), 0.0) > 0 ? toS(payable.getBalanceDue()) + " CR"
				: toS(payable.getBalanceDue());
		addElement(root, "balanceDue", balanceDue);
		String currentBalance = Double.compare(payable.getCurrentBalance(), 0.0) > 0
				? toS(payable.getCurrentBalance()) + " CR" : toS(payable.getCurrentBalance());
		addElement(root, "currentBalance", currentBalance);

		return root.getOwnerDocument();
	}

	Element getRootElement() throws IOException {
		Document foDoc = null;
		Element root = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new IOException(e);
		}
		foDoc = db.newDocument();

		root = foDoc.createElement("root");
		foDoc.appendChild(root);
		return root;

	}

	private void addElement(Node parent, String newNodeName, String textVal) {
		if (textVal == null) {
			return;
		}
		Element newElement = parent.getOwnerDocument().createElement(newNodeName);
		Text elementText = parent.getOwnerDocument().createTextNode(textVal);
		newElement.appendChild(elementText);
		parent.appendChild(newElement);
	}

	private Child findChild(String childId) throws IOException {
		Child child = cc.findChild(childId);
		if (child == null) {
			throw new IOException("Child not found by " + childId);
		}
		return child;
	}

}
