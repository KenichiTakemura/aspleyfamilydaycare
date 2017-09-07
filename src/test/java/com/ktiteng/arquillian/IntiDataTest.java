package com.ktiteng.arquillian;

import com.ktiteng.cdi.Log;
import com.ktiteng.controller.service.ChildController;
import com.ktiteng.controller.service.PaymentController;
import com.ktiteng.controller.service.ReceiptController;
import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.Parent;
import com.ktiteng.entity.service.PaymentSchedule;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.ktiteng.util.Utils.toDate;

public class IntiDataTest extends ArquillianUnitTest {
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
        return false;
    }

    @Override
    public void afterBefore() {
        Paths.get(getPath().toString(), "receipt").toFile().mkdirs();
    }

    private Parent addMe(String... attrs) throws IOException {
        Parent p = cc.findParent(attrs[0], attrs[1]);
        return p != null ? p : cc.addParent(attrs[0], attrs[1], attrs[2], attrs[3]);
    }

    private Child addMyChild(Parent p, String... attrs) throws IOException {
        Child c = cc.findChild(attrs[0], attrs[1]);
        return c != null ? c : cc.addChild(attrs[0], attrs[1], attrs[2], p);
    }

    @Test
    public void p1() throws IOException {
        Parent p = addMe("ParentFirst1", "ParentLast1", "123456789", "username1@google.com");
        Child c = addMyChild(p, "ChildFirst1", "ChildLast1", "Q00085");
        pc.addInitialPayment(c, new
                InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-05-04")
                .setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-05-04"));
        pc.addPaymentSchedule(c, new PaymentSchedule().setDateReceived(toDate("2017-07-18"))
                .setBillingStartDate(toDate("2017-07-03")).setBillingEndDate(toDate("2017-07-16"))
                .setAmountInvoiced(114.00d).setAmountReceived(114.00d).setBalanceDue(0.0d));
        pc.addPaymentSchedule(c, new PaymentSchedule().setDateReceived(toDate("2017-07-31"))
                .setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
                .setAmountInvoiced(123.50d).setAmountReceived(133.00d).setBalanceDue(9.5d));
        pc.addPaymentSchedule(c, new PaymentSchedule().setDateReceived(toDate("2017-08-15"))
                .setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
                .setCurrentBalance(9.5d).setAmountInvoiced(133.00d).setAmountReceived(133.00d).setBalanceDue(9.5d));
    }

    @Test
    public void p2() throws IOException {
        Parent p = addMe("ParentFirst2", "ParentLast2", "2123456789", "username2@google.com");
        Child c = addMyChild(p, "ChildFirst2", "ChildLast2", "Q00086");
        pc.addInitialPayment(c, new
                InitialPayment().setDeposit(95.00).setDepositPaidOn("2017-06-04")
                .setEnrollmentFee(50.00).setEnrollmentFeePaidOn("2017-07-04"));
        pc.addPaymentSchedule(c, new PaymentSchedule().setDateReceived(toDate("2017-08-09"))
                .setBillingStartDate(toDate("2017-07-17")).setBillingEndDate(toDate("2017-07-30"))
                .setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d));
        pc.addPaymentSchedule(c, new PaymentSchedule().setDateReceived(toDate("2017-08-23"))
                .setBillingStartDate(toDate("2017-07-31")).setBillingEndDate(toDate("2017-08-13"))
                .setAmountInvoiced(31.96d).setAmountReceived(31.96d).setBalanceDue(0.0d));
    }

}
