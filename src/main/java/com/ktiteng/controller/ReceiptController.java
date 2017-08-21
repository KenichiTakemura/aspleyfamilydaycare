package com.ktiteng.controller;

import java.io.IOException;

import com.ktiteng.entity.Child;
import com.ktiteng.entity.PaymentSchedule;

public interface ReceiptController {

	void issueReceipt(Child child, PaymentSchedule paymentSchedule) throws IOException;

	void sendReceipt(String childId, String paymentScheduleId) throws IOException;
}
