package com.ktiteng.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.entity.BankDetail;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.manager.PersistenceManager;

public class ChildControllerTest extends ArquillianUnitTest {

	private Path path = Paths.get(System.getProperty("user.home"), ".afdc", "data");

	@Inject
	PersistenceManager pm;

	@Inject
	ChildController cc;

	@Before
	public void before() throws IOException {
		pm.setPath(path);
		FileUtils.deleteDirectory(path.toFile());
		path.toFile().mkdirs();
	}

	@Test
	public void add() throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		assertEquals("pfirst1", cc.findParent(p1.getId()).getFirstName());
		Parent p2 = cc.addParent("pfirst2", "plast2", "0433654801", "test2@gmail.com");
		assertEquals("pfirst2", cc.findParent(p2.getId()).getFirstName());
		assertTrue(Paths.get(path.toString(), "parent.json").toFile().exists());
		p1.setBankDetail(new BankDetail().setAccount("pfirst1"));
		p1 = cc.updateParent(p1);
		Child c1 = cc.addChild("cfirst1", "clast1", p1);
		cc.addChild("cfirst2", "clast2", p2);
		c1.setStartDate("2017-06-12");
		cc.updateChild(c1);
		assertTrue(Paths.get(path.toString(), "child-" + c1.getId() + ".json").toFile().exists());
	}

	@Test(expected=IllegalArgumentException.class)
	public void badEmail() {
		try {
			cc.addParent("pfirst3", "plast3", "0433654800", "test1.gmail.com");
		} catch(IOException ioe) {
			fail();
		}
	}

	@Test
	public void payment() throws IOException {
		Parent p1 = cc.addParent("pfirst1", "plast1", "0433654800", "test1@gmail.com");
		Child c1 = cc.addChild("cfirst1", "clast1", p1);
		Payment payment = cc.findPayment(c1);
		assertNotNull(payment.getId());
		cc.updateInitialPayment(c1, payment, new InitialPayment().setDeposit(150.00));
		cc.updatePaymentSchedule(c1, payment, new PaymentSchedule());
		assertTrue(Paths.get(path.toString(), "payment-" + payment.getId() + ".json").toFile().exists());
	}
}
