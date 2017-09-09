package com.ktiteng.controller.service;

import java.io.IOException;
import java.util.Collection;

import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;

public interface PaymentController {

	Payment findPayment(Child child) throws IOException;
	
	Payment findPayment(String childId) throws IOException;

	PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException;

	Payment addInitialPayment(Child child, InitialPayment initialPayment) throws IOException;

	Payment updateInitialPayment(Child child, InitialPayment initialPayment) throws IOException;
	
	PaymentSchedule addPaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException;

	Collection<Payment> getAllPayments() throws IOException;

	PaymentSchedule updatePaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException;
}
