package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;

public interface PaymentController {
	
	Payment findPayment(Child child) throws IOException;
	
	PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException;

	Payment updateInitialPayment(Child child, InitialPayment initialPayment) throws IOException;

	PaymentSchedule updatePaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException;

}
