package com.ktiteng.afdc;

import static com.ktiteng.util.Utils.toDate;

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
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
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

	private void issue(Child c, InitialPayment ip) throws IOException {
//		Payment p = pc.addInitialPayment(c, ip);
//		InitialPayment initialPayment = p.getInitialPayment();
//		if (!initialPayment.getReceiptDeposit().isIssued()) {
//			rc.issueReceiptDeposit(c, initialPayment);
//		}
//		try {
//			rc.sendReceiptDeposit(c.getId());
//		} catch (Exception e) {
//			log.info("{}", e);
//		}
//		if (!initialPayment.getReceiptEnrollmentFee().isIssued()) {
//			rc.issueReceiptEnrollmentFee(c, initialPayment);
//		}
//		try {
//			rc.sendReceiptEnrollmentFee(c.getId());
//		} catch (Exception e) {
//			log.info("{}", e);
//		}
	}

	private void issue(Child c, PaymentSchedule ps) throws IOException {
//		PaymentSchedule paymentSchedule = pc.addPaymentSchedule(c, ps);
//		if (!paymentSchedule.getReceipt().isIssued()) {
//			rc.issueReceiptWeeks(c, paymentSchedule);
//		}
//		try {
//			rc.sendReceiptWeeks(c.getId(), paymentSchedule.getId());
//		} catch (Exception e) {
//			log.info("{}", e);
//		}
		// log.info("Issued Receipt Child:{} PaymentSchedule:{}", c.getId(),
		// ps.getId());
	}

	private Parent addMe(String... attrs) throws IOException {
		Parent p = cc.findParent(attrs[0], attrs[1]);
		return p != null ? p : cc.addParent(attrs[0], attrs[1], attrs[2], attrs[3]);
	}

	private Child addMyChild(Parent p, String... attrs) throws IOException {
		Child c = cc.findChild(attrs[0], attrs[1]);
		return c != null ? c : cc.addChild(attrs[0], attrs[1], attrs[2], p);
	}

	// @Test
	public void channy() throws IOException {
		Parent p = addMe("Yun Kyung", "Youn", "0428933858", "kattio85@hotmail.com");
		Child c = addMyChild(p, "Channy", "Youn", "Q00085");
		// pc.addInitialPayment(c, new
		// InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-05-04")
		// .setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-05-04"));
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
				.setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
				.setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-07-31"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.50d).setAmountReceived(133.00d).setBalanceDue(9.5d);
		issue(c, ps2);
		PaymentSchedule ps3 = new PaymentSchedule().setDateReceived(toDate("2017-08-15"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(9.5d).setAmountInvoiced(133.00d).setAmountReceived(133.00d).setBalanceDue(9.5d);
		issue(c, ps3);
	}

	// @Test
	public void allan() throws IOException {
		Parent p = addMe("Jihyun", "Broadhurst", "0423313077", "ijiat27@gmail.com");
		Child c = addMyChild(p, "Allan", "Broadhurst", "Q00081");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-09"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

	// @Test
	public void yuna() throws IOException {
		Parent p = addMe("Miyoung", "Cho", "0435262072", "yitmy@naver.com");
		Child c = addMyChild(p, "Yuna", "Cho", "Q00086");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(90.96d).setAmountReceived(90.96d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-21"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setAmountInvoiced(90.96d).setAmountReceived(90.96d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

	// @Test
	public void ruby() throws IOException {
		Parent p = addMe("Seong Wan", "Hong", "0407357839", "minsanghee@gmail.com");
		Child c = addMyChild(p, "Ruby", "Hong", "Q00087");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(18.50d).setAmountInvoiced(131.88d).setAmountReceived(113.38d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-30"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(0.0d).setAmountInvoiced(131.88d).setAmountReceived(131.88d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

	// @Test
	public void leon() throws IOException {
		Parent p = addMe("Eun Ju", "Kim", "0430705329", "rladmswn81@hotmail.com");
		Child c = addMyChild(p, "Leon", "Jeong", "Q00088");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(98.90d).setAmountReceived(98.90d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-22"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(0.0d).setAmountInvoiced(152.16d).setAmountReceived(152.16d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

	@Test
	public void jason() throws IOException {
		Parent p = addMe("Hyeeun", "Lee", "0411431913", "hyeeun79@hotmail.com");
		Child c = addMyChild(p, "Jason", "Lee", "Q000810");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-08"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(87.26d).setAmountReceived(87.26d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-29"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(0.0d).setAmountInvoiced(87.26d).setAmountReceived(87.26d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

	// @Test
	public void ian() throws IOException {
		Parent p = addMe("Sung Joon", "Hong", "0452506955", "jooninau@gmail.com");
		Child c = addMyChild(p, "Ian", "Kim", "Q000811");
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-08"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(114.88d).setAmountReceived(114.88d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-21"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(0.0d).setAmountInvoiced(137.44d).setAmountReceived(137.44d).setBalanceDue(0.0d);
		issue(c, ps2);

	}

	// @Test
	public void louis() throws IOException {
		Parent p = addMe("Eunhee", "Yang", "0431175258", "david@casscale.com.au");
		Child c = addMyChild(p, "Louis", "Oh", "Q000812");
		issue(c, new InitialPayment().setDeposit(266.00d).setDepositPaidOn("2017-07-26").setEnrollmentFee(50.00d)
				.setEnrollmentFeePaidOn("2017-07-26"));
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(213.24d).setAmountReceived(213.24d).setBalanceDue(0.0d);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
				.setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
				.setCurrentBalance(0.0d).setAmountInvoiced(142.16d).setAmountReceived(142.16d).setBalanceDue(0.0d);
		issue(c, ps2);
	}

}
