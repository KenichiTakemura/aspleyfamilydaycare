package com.ktiteng.controller.service;

import java.io.IOException;
import java.util.Collection;

import com.ktiteng.entity.service.Deposit;
import com.ktiteng.entity.service.EnrollmentFee;
import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.Payment;
import com.ktiteng.entity.service.PaymentSchedule;

public interface PaymentController {

	Payment findPayment(String childId) throws IOException;

	void updatePayment(String childId) throws IOException;

	Payable find(String childId, String payableId) throws IOException;

	Deposit findDeposit(String childId) throws IOException;

	Deposit addDeposit(String childId, Deposit deposit) throws IOException;

	Deposit updateDeposit(String childId, Deposit deposit) throws IOException;

	EnrollmentFee findEnrollmentFee(String childId) throws IOException;

	EnrollmentFee addEnrollmentFee(String childId, EnrollmentFee enrollmentFee) throws IOException;

	EnrollmentFee updateEnrollmentFee(String childId, EnrollmentFee enrollmentFee) throws IOException;

	PaymentSchedule findPaymentSchedule(String childId, String paymentScheduleId) throws IOException;

	PaymentSchedule addPaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException;

	PaymentSchedule updatePaymentSchedule(String childId, PaymentSchedule paymentSchedule) throws IOException;

	Collection<Payment> getAllPayments() throws IOException;

}
