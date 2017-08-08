package com.ktiteng.fop;

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
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;

@Default
@ApplicationScoped
public class PdfGeneratorBean implements PdfGenerator {

	@Inject
	@Log
	protected Logger log;

	/** xsl-fo namespace URI */
	protected static String foNS = "http://www.w3.org/1999/XSL/Format";

	@Override
	public Receipt generateReceipt(PaymentSchedule paymentSchedule) throws IOException {
		Receipt receipt = paymentSchedule.getReceipt();
		FopFactory fopFactory = null;
		OutputStream out;
		try {
			fopFactory = FopFactory.newInstance(this.getClass().getResource("/fop/fop.xconf").toURI());
			out = new BufferedOutputStream(new FileOutputStream(new File(receipt.getReceiptLocation())));
		} catch (Exception e) {
			throw new IOException(e);
		}
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xslt = new StreamSource(this.getClass().getResourceAsStream("/fop/receipt.xsl"));
			Transformer transformer = factory.newTransformer(xslt);
			Source src = new DOMSource(convertToDocument(paymentSchedule));
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);

		} catch (Exception e) {

		} finally {
			out.close();
		}
		return null;
	}

	Document convertToDocument(PaymentSchedule paymentSchedule) throws Exception {
		Document foDoc = null;
		Element root = null, ele1 = null, ele2 = null, ele3 = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		foDoc = db.newDocument();

		root = foDoc.createElementNS(foNS, "fo:root");
		foDoc.appendChild(root);

		ele1 = foDoc.createElementNS(foNS, "fo:layout-master-set");
		root.appendChild(ele1);
		ele2 = foDoc.createElementNS(foNS, "fo:simple-page-master");
		ele1.appendChild(ele2);
		ele2.setAttributeNS(null, "master-name", "letter");
		ele2.setAttributeNS(null, "page-height", "11in");
		ele2.setAttributeNS(null, "page-width", "8.5in");
		ele2.setAttributeNS(null, "margin-top", "1in");
		ele2.setAttributeNS(null, "margin-bottom", "1in");
		ele2.setAttributeNS(null, "margin-left", "1in");
		ele2.setAttributeNS(null, "margin-right", "1in");
		ele3 = foDoc.createElementNS(foNS, "fo:region-body");
		ele2.appendChild(ele3);
		ele1 = foDoc.createElementNS(foNS, "fo:page-sequence");
		root.appendChild(ele1);
		ele1.setAttributeNS(null, "master-reference", "letter");
		ele2 = foDoc.createElementNS(foNS, "fo:flow");
		ele1.appendChild(ele2);
		ele2.setAttributeNS(null, "flow-name", "xsl-region-body");
		addElement(ele2, "fo:block", "Hello World!");
		return foDoc;
	}

	/**
	 * Adds an element to the DOM.
	 * 
	 * @param parent
	 *            parent node to attach the new element to
	 * @param newNodeName
	 *            name of the new node
	 * @param textVal
	 *            content of the element
	 */
	protected static void addElement(Node parent, String newNodeName, String textVal) {
		if (textVal == null) {
			return;
		} // use only with text nodes
		Element newElement = parent.getOwnerDocument().createElementNS(foNS, newNodeName);
		Text elementText = parent.getOwnerDocument().createTextNode(textVal);
		newElement.appendChild(elementText);
		parent.appendChild(newElement);
	}
}
