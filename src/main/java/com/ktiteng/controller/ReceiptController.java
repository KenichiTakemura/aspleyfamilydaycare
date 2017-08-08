package com.ktiteng.controller;

import java.io.IOException;

import javax.inject.Inject;

import com.ktiteng.fop.PdfGenerator;

public interface ReceiptController {

	void issueReceipt(String childId, String paymentScheduleId) throws IOException;

}
