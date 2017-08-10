package com.ktiteng.controller.bean;

import static com.ktiteng.util.Utils.toDate;

import javax.inject.Inject;

import org.junit.Test;
import org.w3c.dom.Document;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PdfGenerator;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;
import com.ktiteng.entity.TaxInvoiceSeeder;

public class PdfGeneratorBeanTest extends ArquillianUnitTest {

	@Inject
	PdfGenerator pdfGen;
	@Inject
	TaxInvoiceSeeder tcs;
	@Inject
	ChildController cc;

	@Test
	public void generate() throws Exception {
		ReceiptControllerBean bean = new ReceiptControllerBean();
		Parent p1 = cc.addParent("Mother", "Youn", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("Channy", "Youn", "Q00085", p1);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d);

		Receipt r = new Receipt().setTaxInvoiceId(tcs.nextVal());
		ps.setReceipt(r);
		Document source = bean.convertToDocument(c1, ps);
		pdfGen.generateReceipt(source, getPathStr() + "/test.pdf");
	}

}
