package com.ktiteng.controller.service;

import com.ktiteng.controller.service.ReceiptController.ReceiptType;
import com.ktiteng.entity.service.Payable;
import com.ktiteng.entity.service.Receipt;

import java.io.IOException;

public interface ReceiptController {
	
	enum ReceiptType {
		WEEKS,
		DEPOSIT,
		ENROLLMENT;
		
		public static ReceiptType get(String value) {
			return ReceiptType.valueOf(value.toUpperCase());
		}
	}
	
	Receipt findReceipt(String id) throws IOException;

	Receipt issueReceipt(String childId, Payable payable, ReceiptType type) throws IOException;
	
	void deleteReceipt(String childId, String receiptId, ReceiptType type) throws IOException;

	void sendReceipt(String childId, String receiptId) throws IOException;
	
}
