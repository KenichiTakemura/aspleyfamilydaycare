package com.ktiteng.arquillian;

import static com.ktiteng.util.Utils.toDate;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.PaymentSchedule;

public class IntiTestDataTest extends ArquillianUnitTest {
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

    protected boolean getDeletePath() {
        return true;
    }

    @Override
    public void afterBefore() {
        Paths.get(getPath().toString(), "receipt").toFile().mkdirs();
    }

    @Test
    public void genData() throws IOException {
    	cc.addParent(parent1);
        Child c = cc.addChild(child1);
        pc.addInitialPayment(c, new
                InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-05-04")
                .setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-05-04"));
        pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
                .setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
                .setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d));
        pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-07-31"))
                .setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
                .setAmountInvoiced(123.50d).setAmountReceived(133.00d).setBalanceDue(9.5d));
        pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-08-15"))
                .setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
                .setCurrentBalance(9.5d).setAmountInvoiced(133.00d).setAmountReceived(133.00d).setBalanceDue(9.5d));
    	cc.addParent(parent2);
    	c = cc.addChild(child2);
        pc.addInitialPayment(c, new
                InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-06-04")
                .setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-07-04"));
        pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-08-09"))
                .setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
                .setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d));
        pc.addPaymentSchedule(c.getId(), new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
                .setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
                .setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d));
    }

}
