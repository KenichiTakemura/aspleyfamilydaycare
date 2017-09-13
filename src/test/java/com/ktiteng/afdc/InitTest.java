package com.ktiteng.afdc;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.controller.service.ReceiptController.ReceiptType;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.PaymentSchedule;

public class InitTest extends ArquillianUnitTest {
	@Inject
	@Log
	private Logger log;
	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;
	@Inject
	ReceiptController rc;

	protected Path getPath() {
		return Paths.get(System.getProperty("user.home"), ".afdc", "master");
	}

	protected boolean getDeletePath() {
		return false;
	}

	@Before
	public void before() {
		Paths.get(getPath().toString(), "receipt").toFile().mkdirs();
	}

//	private void issue(Child c, InitialPayment ip) throws IOException {
		// Payment p = pc.addInitialPayment(c, ip);
		// InitialPayment initialPayment = p.getInitialPayment();
		// if (!initialPayment.getReceiptDeposit().isIssued()) {
		// rc.issueReceiptDeposit(c, initialPayment);
		// }
		// try {
		// rc.sendReceiptDeposit(c.getId());
		// } catch (Exception e) {
		// log.info("{}", e);
		// }
		// if (!initialPayment.getReceiptEnrollmentFee().isIssued()) {
		// rc.issueReceiptEnrollmentFee(c, initialPayment);
		// }
		// try {
		// rc.sendReceiptEnrollmentFee(c.getId());
		// } catch (Exception e) {
		// log.info("{}", e);
		// }
//	}

	private void issue(Child c, PaymentSchedule ps) throws IOException {
		PaymentSchedule paymentSchedule = pc.addPaymentSchedule(c.getId(), ps);
		if (!paymentSchedule.getReceipt().isIssued()) {
			rc.issueReceipt(c.getId(), paymentSchedule.getId(), ReceiptType.WEEKS);
		}
		try {
			rc.sendReceipt(c.getId(), paymentSchedule.getId(), ReceiptType.WEEKS);
		} catch (Exception e) {
			log.info("{}", e);
		}
	}

	private Parent addMe(String... attrs) throws IOException {
		Parent p = cc.findParent(attrs[0], attrs[1]);
		return p != null ? p : cc.addParent(new Parent(attrs[0], attrs[1], attrs[2], attrs[3]));
	}

	private Child addMyChild(Parent p, String... attrs) throws IOException {
		Child c = cc.findChild(attrs[0], attrs[1]);
		return c != null ? c : cc.addChild(new Child(attrs[0], attrs[1], attrs[2], p.getId()));
	}

	private Child findChild(String... attrs) throws IOException {
		Child c = cc.findChild(attrs[0], attrs[1]);
		if (c == null) {
			throw new IOException("Cannot find child");
		}
		log.info("{}", c);
		return c;
	}

	@Test
	public void channy() throws IOException {
		Child c = findChild("Channy", "Youn");

	}

	@Test
	public void allan() throws IOException {
		Child c = findChild("Allan", "Broadhurst");
	}

	@Test
	public void yuna() throws IOException {
		Child c = findChild("Yuna", "Cho");
	}

	@Test
	public void ruby() throws IOException {
		Child c = findChild("Ruby", "Hong");
	}

	@Test
	public void leon() throws IOException {
		Child c = findChild("Leon", "Jeong");
	}

	@Test
	public void jason() throws IOException {
		Child c = findChild("Jason", "Lee");
	}

	@Test
	public void ian() throws IOException {
		Child c = findChild("Ian", "Kim");
	}

	@Test
	public void louis() throws IOException {
		Child c = findChild("Louis", "Oh");
	}

}
