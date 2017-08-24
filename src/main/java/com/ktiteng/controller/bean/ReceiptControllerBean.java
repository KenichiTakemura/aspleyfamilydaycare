package com.ktiteng.controller.bean;

import static com.ktiteng.util.Utils.toS;

import java.io.IOException;

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
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PaymentController;
import com.ktiteng.controller.PdfGenerator;
import com.ktiteng.controller.ReceiptController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;

@Default
@ApplicationScoped
public class ReceiptControllerBean implements ReceiptController {
	@Inject
	PdfGenerator pdfGen;
	@Inject
	@Log
	protected Logger log;

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
	public void issueReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException {
		if (paymentSchedule.isReceiptIssued()) {
			throw new IllegalStateException("Already receipt issued.");
		}
		try {
			pdfGen.generateReceipt(convertToDocument(child, paymentSchedule),
					paymentSchedule.getReceipt().getLocation());
			pc.updatePaymentSchedule(child, paymentSchedule.setReceiptIssued(true));
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void sendReceipt(String childId, String paymentScheduleId) throws IOException {
		Child child = cc.findChild(childId);
		if (child == null) {
			throw new IOException("Child not found by " + childId);
		}
		PaymentSchedule paymentSchedule = pc.findPaymentSchedule(child, paymentScheduleId);
		if (paymentSchedule == null) {
			throw new IOException("PaymentSchedule not found by " + paymentScheduleId);
		}
		if (paymentSchedule.isReceiptSent()) {
			throw new IllegalStateException("Already receipt was sent.");
		}
		String to = cc.findParent(child.getParentId()).getEmailAddress();
		if (to == null) {
			throw new IOException("Parent Email not found for " + child.getParentId());
		}
		gmailSender.sendEmail(to, paymentSchedule.getReceipt().getName(), config.getEmailContents(),
				paymentSchedule.getReceipt().getLocation());
		paymentSchedule.setReceiptSent(true);
	}

	Document convertToDocument(Child child, PaymentSchedule paymentSchedule) throws Exception {
		Document foDoc = null;
		Element root = null;
		Receipt receipt = paymentSchedule.getReceipt();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		foDoc = db.newDocument();

		root = foDoc.createElement("root");
		foDoc.appendChild(root);

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

		return foDoc;
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
