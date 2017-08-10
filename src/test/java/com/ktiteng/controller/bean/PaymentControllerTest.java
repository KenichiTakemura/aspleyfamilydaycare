package com.ktiteng.controller.bean;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PaymentController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;

public class PaymentControllerTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;

	@Inject
	PaymentController pc;

	@Test
	public void payment() throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", "Q00085", p1);
		Payment payment = pc.findPayment(c1);
		assertNotNull(payment.getId());
		pc.updateInitialPayment(c1, payment, new InitialPayment().setDeposit(150.00));
		pc.updatePaymentSchedule(c1, payment, new PaymentSchedule());
		assertTrue(Paths.get(getPathStr(), "payment-" + payment.getId() + ".json").toFile().exists());
	}
}
