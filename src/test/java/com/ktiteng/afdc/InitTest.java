package com.ktiteng.afdc;

import static com.ktiteng.util.Utils.toDate;

import java.io.IOException;

import javax.inject.Inject;

import org.junit.Test;

import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.ChildController;
import com.ktiteng.controller.PaymentController;
import com.ktiteng.entity.Child;
import com.ktiteng.entity.Parent;
import com.ktiteng.entity.PaymentSchedule;
import com.ktiteng.entity.Receipt;
import com.ktiteng.entity.TaxInvoiceSeeder;

public class InitTest extends ArquillianUnitTest {

	@Inject
	ChildController cc;
	@Inject
	PaymentController pc;
	@Inject
	TaxInvoiceSeeder tcs;
	// Channy:
	@Test
	public void channy() throws IOException {
		Parent p = cc.addParent("Mother", "Youn", null, "kattio85@hotmail.com");
		Child c = cc.addChild("Channy", null, "Q00085", p);
		PaymentSchedule ps = new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
				.setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
				.setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d);

		Receipt r = new Receipt().setTaxInvoiceId(tcs.nextVal());
		ps.setReceipt(r);
	}

}
