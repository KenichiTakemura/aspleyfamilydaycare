package com.ktiteng.controller.service;

import java.io.IOException;

import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.Receipt;

public interface ReceiptController {
		
	Receipt findReceipt(String receiptId) throws IOException;

	Receipt issueReceipt(String childId, Payable payable) throws IOException;
	
	void deleteReceipt(String childId, String receiptId) throws IOException;

	void sendReceipt(String childId, String receiptId) throws IOException;
	
}
