package com.ktiteng.controller;

import java.io.IOException;
import java.util.Collection;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.InitialPayment;
import com.ktiteng.entity.Payment;
import com.ktiteng.entity.PaymentSchedule;

public interface PaymentController {

	Payment findPayment(Child child) throws IOException;

	PaymentSchedule findPaymentSchedule(Child child, String paymentScheduleId) throws IOException;

	PaymentSchedule findPaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException;

	Payment addInitialPayment(Child child, InitialPayment initialPayment) throws IOException;

	PaymentSchedule addPaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException;

	Collection<Payment> getAllPayments() throws IOException;

	PaymentSchedule updatePaymentSchedule(Child child, PaymentSchedule paymentSchedule) throws IOException;
}
