package com.ktiteng.controller.bean.service;

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
		Receipt receipt = paymentSchedule.getReceipt();
		if (receipt.isIssued()) {
			throw new IllegalStateException("Already receipt issued.");
		}
		try {
			pdfGen.generateReceipt(convertPaymentScheduleToDocument(child, paymentSchedule),
					receipt.getLocation());
			receipt.setIssued(true);
			pc.updatePaymentSchedule(child, paymentSchedule);
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
		Receipt receipt = paymentSchedule.getReceipt();
		if (receipt.isSent()) {
			throw new IllegalStateException("Already receipt was sent.");
		}
		String to = cc.findParent(child.getParentId()).getEmailAddress();
		if (to == null) {
			throw new IOException("Parent Email not found for " + child.getParentId());
		}
		gmailSender.sendEmail(to, receipt.getName(), config.getEmailContents(),
				receipt.getLocation());
		receipt.setSent(true);
		pc.updatePaymentSchedule(child, paymentSchedule);
	}

	Document convertPaymentScheduleToDocument(Child child, PaymentSchedule paymentSchedule) throws Exception {
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

	@Override
	public void issueReceiptDeposit(Child child, InitialPayment initialPayment) throws IOException {
		Receipt receipt = initialPayment.getReceiptDeposit();
		if (receipt.isIssued()) {
			throw new IllegalStateException("Already receipt issued.");
		}
		try {
//			pdfGen.generateReceipt(convertPaymentScheduleToDocument(child, initialPayment),
//					receipt.getLocation());
			receipt.setIssued(true);
			pc.updatePaymentSchedule(child, initialPayment);
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void issueReceiptEnrollmentFee(Child child, InitialPayment initialPayment) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendReceiptDeposit(String childId, String initialPaymentId) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendReceiptEnrollmentFee(String childId, String initialPaymentId) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
