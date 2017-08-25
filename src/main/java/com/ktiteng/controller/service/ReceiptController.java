package com.ktiteng.controller.service;

import java.io.IOException;

import com.ktiteng.entity.service.Child;
import com.ktiteng.entity.service.InitialPayment;
import com.ktiteng.entity.service.PaymentSchedule;

public interface ReceiptController {

	void issueReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException;

	void sendReceipt(String childId, String paymentScheduleId) throws IOException;

	void issueReceiptDeposit(Child child, InitialPayment initialPayment) throws IOException;

	void issueReceiptEnrollmentFee(Child child, InitialPayment initialPayment) throws IOException;

	void sendReceiptDeposit(String childId, String initialPaymentId) throws IOException;

	void sendReceiptEnrollmentFee(String childId, String initialPaymentId) throws IOException;

}
