package com.ktiteng.controller.bean.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;
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

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.PdfGenerator;
import com.ktiteng.controller.service.ReceiptController.ReceiptType;
import com.ktiteng.entity.service.Receipt;

@Default
@ApplicationScoped
public class PdfGeneratorBean implements PdfGenerator {

	@Inject
	@Log
	private Logger log;

	@Override
	public Receipt generateReceipt(Document source, String pdfLocation, ReceiptType type) throws IOException {
		if (type == ReceiptType.WEEKS) {
		return this.generateReceipt(source, "/fop/weeksReceipt.xsl", pdfLocation);
		} else if (type == ReceiptType.DEPOSIT) {
			return this.generateReceipt(source, "/fop/depositReceipt.xsl", pdfLocation);
		} else if (type == ReceiptType.ENROLLMENT) {
			return this.generateReceipt(source, "/fop/enrollmentfeeReceipt.xsl", pdfLocation);
		} else {
			throw new IOException("Unknown ReceiptType " + type);
		}
	}

	private Receipt generateReceipt(Document source, String xsl, String pdfPath) throws IOException {
		FopFactory fopFactory = null;
		OutputStream out;
		try {
			fopFactory = FopFactory.newInstance(this.getClass().getResource("/fop/fop.xconf").toURI());
			File pdf = new File(pdfPath);
			if (!pdf.getParentFile().exists()) {
				pdf.getParentFile().mkdirs();
			}
			out = new BufferedOutputStream(new FileOutputStream(new File(pdfPath)));
		} catch (Exception e) {
			throw new IOException(e);
		}
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);
			TransformerFactory factory = TransformerFactory.newInstance();
			Source xslt = new StreamSource(this.getClass().getResourceAsStream(xsl));
			Transformer transformer = factory.newTransformer(xslt);
			Source src = new DOMSource(source);
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(src, res);

		} catch (Exception e) {

		} finally {
			out.close();
		}
		return null;
	}

}
