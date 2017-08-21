package com.ktiteng.afdc;

import static com.ktiteng.util.Utils.toDate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PaymentController;
import com.ktiteng.controller.ReceiptController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.PaymentSchedule;

public class InitTest extends ArquillianUnitTest {

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

	private void issue(Child c, PaymentSchedule ps) throws IOException {
		//pc.updatePaymentSchedule(c, ps);
		//rc.issueReceipt(c, ps);
		//rc.sendReceipt(c.getId(), ps.getId());
		//log.info("Issued Receipt Child:{} PaymentSchedule:{}", c.getId(), ps.getId());
	}

	@Test
	public void channy() throws IOException {
		Parent p = cc.addParent("Yun Kyung", "Youn", "0428933858", "kattio85@hotmail.com");
		Child c = cc.addChild("Channy", "Youn", "Q00085", p);
		pc.updateInitialPayment(c, new InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-05-04")
				.setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-05-04"));
		PaymentSchedule ps1 = new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
				.setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
				.setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps1);
		issue(c, ps1);
		PaymentSchedule ps2 = new PaymentSchedule().setDateReceived(toDate("2017-07-31"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(123.50d).setAmountReceived(133.00d).setBalanceDue(9.5d);
		issue(c, ps2);
	}

	@Test
	public void allan() throws IOException {
		Parent p = cc.addParent("Jihyun", "Broadhurst", "0423313077", "ijiat27@gmail.com");
		Child c = cc.addChild("Allan", "Broadhurst", "Q00081", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-09"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	@Test
	public void yuna() throws IOException {
		Parent p = cc.addParent("Miyoung", "Cho", "0435262072", "yitmy@naver.com");
		Child c = cc.addChild("Yuna", "Cho", "Q00086", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setAmountInvoiced(90.96d).setAmountReceived(90.96d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	@Test
	public void ruby() throws IOException {
		Parent p = cc.addParent("Seong Wan", "Hong", "0407357839", "minsanghee@gmail.com");
		Child c = cc.addChild("Ruby", "Hong", "Q00087", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(18.50d).setAmountInvoiced(131.88d).setAmountReceived(113.38d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	@Test
	public void leon() throws IOException {
		Parent p = cc.addParent("Eun Ju", "Kim", "0430705329", "rladmswn81@hotmail.com");
		Child c = cc.addChild("Leon", "Jeong", "Q00088", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-07"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(98.90d).setAmountReceived(98.90d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	@Test
	public void jason() throws IOException {
		Parent p = cc.addParent("Hyeeun", "Lee", "0411431913", "hyeeun79@hotmail.com");
		Child c = cc.addChild("Jason", "Lee", "Q000810", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-08"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(87.26d).setAmountReceived(87.26d).setBalanceDue(0.0d);
		pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	@Test
	public void ian() throws IOException {
		Parent p = cc.addParent("Sung Joon", "Hong", "0452506955", "jooninau@gmail.com");
		Child c = cc.addChild("Ian", "Kim", "Q000811", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-08-08"))
				.setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
				.setCurrentBalance(0.0d).setAmountInvoiced(114.88d).setAmountReceived(114.88d).setBalanceDue(0.0d);
		ps = pc.updatePaymentSchedule(c, ps);
		issue(c, ps);
	}

	// Louis Oh/Eunhee Yang, Q000812, 0431175258, ohyouna@optusnet.com.au

}
