package com.ktiteng.controller.service;

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

	Receipt getReceipt(String childId, String id, ReceiptType type) throws IOException;

	void issueReceipt(String childId, String id, ReceiptType type) throws IOException;
	
	void deleteReceipt(String childId, String id, ReceiptType type) throws IOException;

	void sendReceipt(String childId, String id, ReceiptType type) throws IOException;
	
}
