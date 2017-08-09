package com.ktiteng.controller;

import java.io.IOException;

public interface ReceiptController {

	void issueReceipt(String childId, String paymentScheduleId) throws IOException;

}
