package com.ktiteng.controller.bean.service;

import static com.ktiteng.util.Utils.toDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.util.Utils;

public class PaymentControllerTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;

	@Test
	public void addPaymentSchedule() throws IOException {
		cc.addParent(parent1);
		Child c = cc.addChild(child1);
		Payment p = pc.findPayment(c.id());
		assertNotNull(p);
		pc.addDeposit(c.id(), new Deposit().setAmountInvoiced(95.00d).setDateReceived(toDate("2017-05-04")));
		pc.addEnrollmentFee(c.id(),
				new EnrollmentFee().setAmountInvoiced(50.00d).setDateReceived(toDate("2017-05-06")));

		PaymentSchedule ps = pc.addPaymentSchedule(c.id(), new PaymentSchedule()
				.setBillingStartDate(Utils.toDate("2017-08-01")).setBillingEndDate(Utils.toDate("2017-08-15")));
		assertTrue(Paths.get(getPathStr("service"), "payment-" + p.id() + ".json").toFile().exists());
		log.info("ps {}", ps.id());
		assertEquals(ps.id(), pc.addPaymentSchedule(c.id(), ps).id());
	}

	@Test(expected = IOException.class)
	public void updatePaymentSchedule() throws IOException {
		cc.addParent(parent2);
		Child c = cc.addChild(child2);
		pc.updatePaymentSchedule(c.id(), new PaymentSchedule().setBillingStartDate(Utils.toDate("2017-08-01"))
				.setBillingEndDate(Utils.toDate("2017-08-15")));
	}

	@Test
	public void Deposit() throws IOException {
		cc.addParent(parent3);
		Child c = cc.addChild(child3);
		pc.addDeposit(c.id(), new Deposit().setAmountInvoiced(95.00d).setDateReceived(toDate("2017-05-04")));
		assertNull(pc.findPaymentSchedule(c.id(), "123"));
	}
}
