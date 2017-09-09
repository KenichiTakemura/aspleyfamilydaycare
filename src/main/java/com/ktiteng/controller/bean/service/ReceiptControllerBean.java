package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toS;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.ktiteng.cdi.Config;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.AfdcConfig;
import com.ktiteng.controller.bean.GmailSender;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.PdfGenerator;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;

@Default
@ApplicationScoped
public class ReceiptControllerBean implements ReceiptController {
	@Inject
	PdfGenerator pdfGen;

	@Inject
	@Log
	private Logger log;

	@Inject
	GmailSender gmailSender;

	@Inject
	ChildController cc;

	@Inject
	PaymentController pc;

	@Inject
	@Config
	AfdcConfig config;

	private Child findChild(String childId) throws IOException {
		Child child = cc.findChild(childId);
		if (child == null) {
			throw new IOException("Child not found by " + childId);
		}
		return child;
	}

	private PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException {
		PaymentSchedule paymentSchedule = pc.findPaymentSchedule(child, paymentScheduleId);
		if (paymentSchedule == null) {
			throw new IOException("PaymentSchedule not found by " + paymentScheduleId);
		}
		return paymentSchedule;
	}

	@Override
	public void issueReceipt(String childId, String id, ReceiptType type) throws IOException {
		log.info("issueReceipt childId={}, id={}, type={}", childId, id, type);
		Child child = findChild(childId);
		Receipt receipt;
		Document document;
		PaymentSchedule paymentSchedule = null;
		InitialPayment initialPayment = null;
		try {
			if (type == ReceiptType.WEEKS) {
				paymentSchedule = findPaymentSchedule(child, id);
				receipt = paymentSchedule.getReceipt();
				document = convertPaymentScheduleToDocument(child, paymentSchedule);
			} else if (type == ReceiptType.DEPOSIT) {
				initialPayment = pc.findPayment(childId).getInitialPayment();
				receipt = initialPayment.getReceiptDeposit();
				document = convertDepositToDocument(child, initialPayment);
			} else if (type == ReceiptType.ENROLLMENT) {
				initialPayment = pc.findPayment(childId).getInitialPayment();
				receipt = initialPayment.getReceiptEnrollmentFee();
				document = convertEnrollmentFeeToDocument(child, initialPayment);
			} else {
				throw new IOException("Unknown ReceiptType " + type);
			}
			if (receipt.isIssued()) {
				throw new IllegalStateException("Already receipt issued.");
			}
			pdfGen.generateReceipt(document, receipt.getLocation(), type);
			receipt.setIssued(true);
			if (type == ReceiptType.WEEKS) {
				pc.updatePaymentSchedule(child, paymentSchedule);
			} else {
				pc.updateInitialPayment(child, initialPayment);
			}
		} catch (Exception e) {
			log.warn("Cannot generate a pdf receipt.", e);
			throw new IOException(e);
		}
	}

	@Override
	public void sendReceipt(String childId, String id, ReceiptType type) throws IOException {
		Child child = findChild(childId);
		Receipt receipt = null;
		PaymentSchedule paymentSchedule = null;
		InitialPayment initialPayment = null;
		if (type == ReceiptType.WEEKS) {
			paymentSchedule = findPaymentSchedule(child, id);
			receipt = paymentSchedule.getReceipt();
		} else if (type == ReceiptType.DEPOSIT) {
			initialPayment = pc.findPayment(child).getInitialPayment();
			receipt = initialPayment.getReceiptDeposit();
		} else if (type == ReceiptType.ENROLLMENT) {
			initialPayment = pc.findPayment(child).getInitialPayment();
			receipt = initialPayment.getReceiptEnrollmentFee();
		}
		if (sendReceipt(child, receipt)) {
			if (type == ReceiptType.WEEKS) {
				pc.updatePaymentSchedule(child, paymentSchedule);
			} else {
				pc.updateInitialPayment(child, initialPayment);
			}
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
			receipt.setSentAt(LocalDateTime.now());
		} else {
			log.info("Receipt was already sent");
			return false;
		}
		return true;
	}

	Document convertPaymentScheduleToDocument(Child child, PaymentSchedule paymentSchedule) throws Exception {
		Element root = getRootElement();
		Receipt receipt = paymentSchedule.getReceipt();
		addElement(root, "date", toS(paymentSchedule.getDateReceived()));
		addElement(root, "receiptNumber", receipt.getTaxInvoiceId());
		addElement(root, "childName", child.getName());
		addElement(root, "childNumber", child.getChildNumber());
		addElement(root, "weekStart", toS(paymentSchedule.getBillingStartDate()));
		addElement(root, "weekEnd", toS(paymentSchedule.getBillingEndDate()));
		String currentBalance = Double.compare(paymentSchedule.getCurrentBalance(), 0.0) > 0
				? toS(paymentSchedule.getCurrentBalance()) + " CR" : toS(paymentSchedule.getCurrentBalance());
		addElement(root, "currentBalance", currentBalance);
		addElement(root, "accountAmount", toS(paymentSchedule.getAmountInvoiced()));
		addElement(root, "accountPaid", toS(paymentSchedule.getAmountReceived()));
		String balanceDue = Double.compare(paymentSchedule.getBalanceDue(), 0.0) > 0
				? toS(paymentSchedule.getBalanceDue()) + " CR" : toS(paymentSchedule.getBalanceDue());
		addElement(root, "balanceDue", balanceDue);

		return root.getOwnerDocument();
	}

	Document convertDepositToDocument(Child child, InitialPayment initialPayment) throws Exception {
		Element root = getRootElement();
		Receipt receipt = initialPayment.getReceiptDeposit();

		addElement(root, "date", toS(initialPayment.getDepositPaidOn()));
		addElement(root, "receiptNumber", receipt.getTaxInvoiceId());
		addElement(root, "childName", child.getName());
		addElement(root, "childNumber", child.getChildNumber());
		addElement(root, "depositAmount", toS(initialPayment.getDeposit()));
		addElement(root, "accountAmount", toS(initialPayment.getDeposit()));
		addElement(root, "accountPaid", toS(initialPayment.getDeposit()));
		addElement(root, "depositDue", toS(0.0));

		return root.getOwnerDocument();
	}

	Document convertEnrollmentFeeToDocument(Child child, InitialPayment initialPayment) throws Exception {
		Element root = getRootElement();
		Receipt receipt = initialPayment.getReceiptEnrollmentFee();
		addElement(root, "date", toS(initialPayment.getEnrollmentFeePaidOn()));
		addElement(root, "receiptNumber", receipt.getTaxInvoiceId());
		addElement(root, "childName", child.getName());
		addElement(root, "childNumber", child.getChildNumber());
		addElement(root, "enrollmentFeeAmount", toS(initialPayment.getEnrollmentFee()));
		addElement(root, "accountAmount", toS(initialPayment.getEnrollmentFee()));
		addElement(root, "accountPaid", toS(initialPayment.getEnrollmentFee()));
		addElement(root, "enrollmentFeeDue", toS(0.0));

		return root.getOwnerDocument();
	}

	Element getRootElement() throws Exception {
		Document foDoc = null;
		Element root = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
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

}
