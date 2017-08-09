package com.ktiteng.fop;

import static com.ktiteng.util.Utils.toS;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import com.ktiteng.cdi.Log;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;

@Default
@ApplicationScoped
public class PdfGeneratorBean implements PdfGenerator {

	@Inject
	@Log
	protected Logger log;

	@Override
	public Receipt generateReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException {
		Receipt receipt = paymentSchedule.getReceipt();
		FopFactory fopFactory = null;
		OutputStream out;
		try {
			fopFactory = FopFactory.newInstance(this.getClass().getResource("/fop/fop.xconf").toURI());
			out = new BufferedOutputStream(new FileOutputStream(new File(receipt.getLocation())));
		} catch (Exception e) {
			throw new IOException(e);
		}
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xslt = new StreamSource(this.getClass().getResourceAsStream("/fop/receipt.xsl"));
			Transformer transformer = factory.newTransformer(xslt);
			Source src = new DOMSource(convertToDocument(child, paymentSchedule));
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);

		} catch (Exception e) {

		} finally {
			out.close();
		}
		return null;
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
		addElement(root, "accountAmount", toS(paymentSchedule.getAmountInvoiced()));
		addElement(root, "thisPayment", toS(paymentSchedule.getAmountReceived()));
		addElement(root, "balanceDue", toS(paymentSchedule.getBalanceDue()));
		
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
