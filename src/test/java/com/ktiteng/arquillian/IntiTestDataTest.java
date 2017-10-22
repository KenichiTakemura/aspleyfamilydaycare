package com.ktiteng.arquillian;

import static com.ktiteng.util.Utils.toDate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.PaymentSchedule;

public class IntiTestDataTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;
	@Inject
	ReceiptController rc;

	@Override
	protected Path getPath() {
		return Paths.get("src/test/resources/data");
	}

	protected boolean getDeletePath() {
		return true;
	}

	@Override
	public void afterBefore() {
		Paths.get(getPath().toString(), "receipt").toFile().mkdirs();
	}

	@Test
	public void genData() throws IOException {
		cc.addParent(parent1);
		Child c = cc.addChild(child1);
		rc.issueReceipt(c.id(), pc.addDeposit(c.id(),
				new Deposit().setAmountInvoiced(95.00d).setDateReceived(toDate("2017-05-04"))));
		rc.issueReceipt(c.id(), pc.addEnrollmentFee(c.id(),
				new EnrollmentFee().setAmountInvoiced(50.00d).setDateReceived(toDate("2017-05-06"))));
		rc.issueReceipt(c.id(),
				pc.addPaymentSchedule(c.id(),
						new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
								.setBillingStartDate(toDate("2017-07-03")).setAmountInvoiced(114.00d)
								.setAmountReceived(114.00d).setBalanceDue(0.0d)));
		rc.issueReceipt(c.id(),
				pc.addPaymentSchedule(c.id(),
						new PaymentSchedule().setDateReceived(toDate("2017-07-31"))
								.setBillingStartDate(toDate("2017-07-17")).setAmountInvoiced(123.50d)
								.setAmountReceived(133.00d).setBalanceDue(9.5d)));
		rc.issueReceipt(c.id(),
				pc.addPaymentSchedule(c.id(),
						new PaymentSchedule().setDateReceived(toDate("2017-08-15"))
								.setBillingStartDate(toDate("2017-07-31")).setCurrentBalance(9.5d)
								.setAmountInvoiced(133.00d).setAmountReceived(133.00d).setBalanceDue(9.5d)));
		cc.addParent(parent2);
		c = cc.addChild(child2);
		pc.addDeposit(c.id(), new Deposit().setAmountInvoiced(195.00d).setDateReceived(toDate("2017-06-04")));
		pc.addEnrollmentFee(c.id(),
				new EnrollmentFee().setAmountInvoiced(50.00d).setDateReceived(toDate("2017-06-06")));
		pc.addPaymentSchedule(c.id(), new PaymentSchedule().setDateReceived(toDate("2017-08-09"))
				.setBillingStartDate(toDate("2017-07-17")).setAmountInvoiced(31.96d).setAmountReceived(31.96d));
		pc.addPaymentSchedule(c.id(), new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
				.setBillingStartDate(toDate("2017-07-31")).setAmountInvoiced(31.96d).setAmountReceived(31.96d));
	}

}
