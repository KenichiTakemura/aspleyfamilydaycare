package com.ktiteng.controller.service;

import java.io.IOException;

import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.PaymentSchedule;

public interface ReceiptController {

	void issueReceiptWeeks(Child child, PaymentSchedule paymentSchedule) throws IOException;

	void sendReceiptWeeks(String childId, String paymentScheduleId) throws IOException;

	void issueReceiptDeposit(Child child, InitialPayment initialPayment) throws IOException;

	void issueReceiptEnrollmentFee(Child child, InitialPayment initialPayment) throws IOException;

	void sendReceiptDeposit(String childId) throws IOException;

	void sendReceiptEnrollmentFee(String childId) throws IOException;

}
