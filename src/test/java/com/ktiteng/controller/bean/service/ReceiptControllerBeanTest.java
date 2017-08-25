package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toDate;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;
import com.ktiteng.entity.service.TaxInvoiceSeeder;


public class ReceiptControllerBeanTest {
	@Inject
	@Log
	protected Logger log;
	@Inject
	TaxInvoiceSeeder tcs;
	@Inject
	ChildController cc;

	@Test
	public void convertToDocument() throws Exception {
		ReceiptControllerBean bean = new ReceiptControllerBean();
		Parent p1 = cc.addParent("Mother", "Youn", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("Channy", "Youn", "Q00085", p1);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d);

		Receipt r = new Receipt().setTaxInvoiceId(tcs.nextVal());
		ps.setReceipt(r);
		Document root = bean.convertToDocument(c1, ps);
		DOMImplementationLS domImplLS = (DOMImplementationLS) root.getImplementation();
		LSSerializer serializer = domImplLS.createLSSerializer();
		String str = serializer.writeToString(root);
		log.debug("{}", str);
	}
}
