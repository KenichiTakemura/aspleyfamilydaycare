package com.ktiteng.arquillian;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class LoadTest extends ArquillianUnitTest {
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

	@Override
	public void afterBefore() {

	}

	@Test
	public void loadParent() throws IOException {
		Collection<Parent> parents = cc.getAllParents();
		parents.stream().forEach(p -> log.info("Parent " + p.getFirstName()));
		assertEquals(2, parents.size());
	}

	@Test
	public void loadChildren() throws IOException {
		Collection<Child> children = cc.getAllChildren();
		children.stream().forEach(c -> log.info("Child " + c.getFirstName()));
		assertEquals(2, children.size());
	}

	@Test
	public void loadPayments() throws IOException {
		Collection<Payment> payments = pc.getAllPayments();
		payments.stream().forEach(p -> log.info("Payment " + p.getId()));
		assertEquals(2, payments.size());
		PaymentSchedule ps1 = payments.stream().findFirst().get().getPaymentSchedule().get(0);
		PaymentSchedule ps2 = payments.stream().findFirst().get().getPaymentSchedule().get(1);
		assertEquals(114.00d, ps1.getAmountInvoiced(), 0.0d);
		assertEquals(123.5d, ps2.getAmountInvoiced(), 0.0d);
	}
}
