package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toDate;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.afdc.InvoiceType;
import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PdfGenerator;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.PaymentSchedule;
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
		cc.addParent(parent1);
		Child c = cc.addChild(child1);
		PaymentSchedule paymentSchedule = new PaymentSchedule().setDateReceived(toDate("2017-08-12"))
				.setBillingStartDate(toDate("2017-07-16")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.12d).setAmountReceived(133.23d).setBalanceDue(9.5d);

		pdfGen.generateReceipt(bean.convertToDocument(c, paymentSchedule), getPathStr() + "/weeks.pdf",
				InvoiceType.WEEKS);

		bean = new ReceiptControllerBean();
		Deposit deposit = new Deposit().setDateReceived(toDate("2017-08-12")).setAmountInvoiced(123.12d);
		EnrollmentFee enrollmentFee = new EnrollmentFee().setDateReceived(toDate("2017-08-12"))
				.setAmountInvoiced(123.12d);

		pdfGen.generateReceipt(bean.convertToDocument(c, deposit), getPathStr() + "/deposit.pdf", InvoiceType.DEPOSIT);

		pdfGen.generateReceipt(bean.convertToDocument(c, enrollmentFee), getPathStr() + "/enrollmentfee.pdf",
				InvoiceType.ENROLLMENT);

	}

}
