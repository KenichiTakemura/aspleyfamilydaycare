package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;

public interface PaymentController {
	
	Payment findPayment(Child child) throws IOException;

	Payment updateInitialPayment(Child child, Payment payment, InitialPayment initialPayment) throws IOException;

	Payment updatePaymentSchedule(Child child, Payment payment, PaymentSchedule paymentSchedule) throws IOException;

}
