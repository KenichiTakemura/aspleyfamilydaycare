package com.ktiteng.controller.bean.service;

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
import com.ktiteng.entity.service.Parent;
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
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", "Q00085", p1);
		assertNotNull(pc.findPayment(c1));
		Payment payment = pc.addInitialPayment(c1, new InitialPayment().setDeposit(150.00));
		PaymentSchedule ps = pc.addPaymentSchedule(c1, new PaymentSchedule()
				.setBillingStartDate(Utils.toDate("2017-08-01")).setBillingEndDate(Utils.toDate("2017-08-15")));
		assertTrue(Paths.get(getPathStr(), "payment-" + payment.getId() + ".json").toFile().exists());
		log.info("ps {}", ps.getId());
		try {
			pc.addPaymentSchedule(c1, ps);
			fail();
		} catch (IOException e) {

		}
	}

	@Test(expected = IOException.class)
	public void updatePaymentSchedule() throws IOException {
		Parent p1 = cc.addParent("pfirst2", "plast2", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst2", "clast2", "Q00085", p1);
		pc.updatePaymentSchedule(c1, new PaymentSchedule().setBillingStartDate(Utils.toDate("2017-08-01"))
				.setBillingEndDate(Utils.toDate("2017-08-15")));
	}

	@Test
	public void initialPayment() throws IOException {
		Parent p1 = cc.addParent("pfirst3", "plast3", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst3", "clast3", "Q00085", p1);
		pc.addInitialPayment(c1, new InitialPayment().setDeposit(150.00));
		assertNull(pc.findPaymentSchedule(c1, "123"));
	}
}
