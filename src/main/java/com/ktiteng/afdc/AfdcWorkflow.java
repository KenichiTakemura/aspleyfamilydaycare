package com.ktiteng.afdc;

import java.io.IOException;

public interface AfdcWorkflow {

	String confirmBiWeeklyPayment(String childId, String weekStart, String amount, String receivedDate) throws IOException;
	
	String confirmDeposit(String childId, String amount, String receivedDate) throws IOException;
	
	String confirmEntollmentFee(String childId, String amount, String receivedDate) throws IOException;
	
	void sendReceipt(String childId, String payableId) throws IOException;
	
	void sendNewsLetter(String month, String filename) throws IOException;
}
