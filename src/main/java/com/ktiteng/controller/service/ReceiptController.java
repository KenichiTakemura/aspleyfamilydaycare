package com.ktiteng.controller.service;

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

	void issueReceipt(String childId, String id, ReceiptType type) throws IOException;
	
	void deleteReceipt(String childId, String id, ReceiptType type) throws IOException;

	void sendReceipt(String childId, String id, ReceiptType type) throws IOException;
	
}
