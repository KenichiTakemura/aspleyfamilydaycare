package com.ktiteng.afdc;

import java.io.IOException;

public interface AfdcWorkflow {

	void confirmPayment(String childId, InvoiceType type, String weekStart, String amount, String receivedDate) throws IOException;
	
	void sendReceipt(String childId, String payableId) throws IOException;
}
