package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toDate;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;
import org.w3c.dom.Document;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.bean.service.ReceiptControllerBean;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PdfGenerator;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.entity.service.Receipt;
import com.ktiteng.entity.service.TaxInvoiceSeeder;

public class PdfGeneratorBeanTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	PdfGenerator pdfGen;
	@Inject
	TaxInvoiceSeeder tcs;
	@Inject
	ChildController cc;

	@Test
	public void generate() throws Exception {
		ReceiptControllerBean bean = new ReceiptControllerBean();
		Child c = cc.addChild(child1);
		PaymentSchedule paymentSchedule = new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d);

		paymentSchedule.setReceipt(new Receipt().setTaxInvoiceId(tcs.nextVal()));
		pdfGen.generateWeeksReceipt(bean.convertPaymentScheduleToDocument(c, paymentSchedule),
				getPathStr() + "/weeks.pdf");

		bean = new ReceiptControllerBean();
		InitialPayment initialPayment = new InitialPayment().setDepositPaidOn(toDate("2017-08-12")).setDeposit(123.12d)
				.setEnrollmentFeePaidOn(toDate("2017-08-12")).setEnrollmentFee(99.99d);

		initialPayment.setReceiptDeposit(new Receipt().setTaxInvoiceId(tcs.nextVal()));
		pdfGen.generateDepositReceipt(bean.convertDepositToDocument(c, initialPayment), getPathStr() + "/deposit.pdf");
		initialPayment.setReceiptEnrollmentFee(new Receipt().setTaxInvoiceId(tcs.nextVal()));
		pdfGen.generateEnrollmentFeeReceipt(bean.convertEnrollmentFeeToDocument(c, initialPayment),
				getPathStr() + "/enrollmentfee.pdf");

	}

}
