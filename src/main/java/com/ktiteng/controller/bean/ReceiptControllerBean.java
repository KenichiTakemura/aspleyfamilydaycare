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

import com.ktiteng.afdc.AFDC;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.ChildController;
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

	@Override
	public void issueReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException {
		try {
			pdfGen.generateReceipt(convertToDocument(child, paymentSchedule),
					paymentSchedule.getReceipt().getLocation());
		} catch (Exception e) {
			throw new IOException(e);
		}
	}

	@Override
	public void sendReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException {
		String to = cc.findParent(child.getParentId()).getEmailAddress();
		gmailSender.sendEmail(to, paymentSchedule.getReceipt().getName(), AFDC.EmailContent, paymentSchedule.getReceipt().getLocation());
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
				? toS(paymentSchedule.getCurrentBalance()) + "CR" : toS(paymentSchedule.getCurrentBalance());
		addElement(root, "currentBalance", currentBalance);
		addElement(root, "accountAmount", toS(paymentSchedule.getAmountInvoiced()));
		addElement(root, "accountPaid", toS(paymentSchedule.getAmountReceived()));
		String balanceDue = Double.compare(paymentSchedule.getBalanceDue(), 0.0) > 0
				? toS(paymentSchedule.getBalanceDue()) + "CR" : toS(paymentSchedule.getBalanceDue());
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
