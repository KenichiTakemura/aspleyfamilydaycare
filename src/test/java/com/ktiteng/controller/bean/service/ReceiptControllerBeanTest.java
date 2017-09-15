package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toDate;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;
import com.ktiteng.entity.service.TaxInvoiceSeeder;

public class ReceiptControllerBeanTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	TaxInvoiceSeeder tcs;
	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;
	@Inject
	ReceiptController rc;
	
	@Test
	public void deleteReceipt() throws Exception {
		cc.addParent(parent1);
		Child c = cc.addChild(child1);
		PaymentSchedule ps = pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16"))
				.setAmountInvoiced(123.12d));
		Receipt r = rc.issueReceipt(c.getId(), ps);
		assertTrue(Paths.get(r.getLocation()).toFile().exists());
		rc.deleteReceipt(c.getId(), r.getId());
		assertFalse(Paths.get(r.getLocation()).toFile().exists());
	}

	@Test(expected=IOException.class)
	public void deleteReceiptNotIssued() throws Exception {
		cc.addParent(parent3);
		Child c = cc.addChild(child3);
		PaymentSchedule ps = pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d));
		rc.deleteReceipt(c.getId(), ps.getReceiptId());
	}

	@Test
	public void convertToDocument() throws Exception {
		ReceiptControllerBean bean = new ReceiptControllerBean();
		cc.addParent(parent2);
		Child c = cc.addChild(child2);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d);

		Document root = bean.convertToDocument(c, ps);
		DOMImplementationLS domImplLS = (DOMImplementationLS) root.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String str = serializer.writeToString(root);
		log.debug("{}", str);
	}
}
