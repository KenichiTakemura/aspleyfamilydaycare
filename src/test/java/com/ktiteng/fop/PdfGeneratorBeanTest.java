package com.ktiteng.fop;

import javax.inject.Inject;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.entity.PaymentSchedule;

public class PdfGeneratorBeanTest extends ArquillianUnitTest {
	
	@Inject
	PdfGenerator pdfGen;

	@Test
	public void convertToDocument() throws Exception {
		PdfGeneratorBean bean = new PdfGeneratorBean();
		Document root = bean.convertToDocument(new PaymentSchedule().setDateReceived("2017-07-12"));
		DOMImplementationLS domImplLS = (DOMImplementationLS) root.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String str = serializer.writeToString(root);
		log.debug("{}", str);
	}
}
