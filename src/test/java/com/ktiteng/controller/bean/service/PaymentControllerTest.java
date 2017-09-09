package com.ktiteng.controller.bean.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;
import com.ktiteng.util.Utils;

public class PaymentControllerTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;

	@Test
	public void addPaymentSchedule() throws IOException {
		Child c = cc.addChild(child1);
		assertNotNull(pc.findPayment(c));
		Payment payment = pc.addInitialPayment(c, new InitialPayment().setDeposit(150.00));
		PaymentSchedule ps = pc.addPaymentSchedule(c, new PaymentSchedule()
				.setBillingStartDate(Utils.toDate("2017-08-01")).setBillingEndDate(Utils.toDate("2017-08-15")));
		assertTrue(Paths.get(getPathStr(), "payment-" + payment.getId() + ".json").toFile().exists());
		log.info("ps {}", ps.getId());
		assertEquals(ps.getId(), pc.addPaymentSchedule(c, ps).getId());
	}

	@Test(expected = IOException.class)
	public void updatePaymentSchedule() throws IOException {
		Child c = cc.addChild(child2);
		pc.updatePaymentSchedule(c, new PaymentSchedule().setBillingStartDate(Utils.toDate("2017-08-01"))
				.setBillingEndDate(Utils.toDate("2017-08-15")));
	}

	@Test
	public void initialPayment() throws IOException {
		Child c = cc.addChild(child3);
		pc.addInitialPayment(c, new InitialPayment().setDeposit(150.00));
		assertNull(pc.findPaymentSchedule(c, "123"));
	}
}
