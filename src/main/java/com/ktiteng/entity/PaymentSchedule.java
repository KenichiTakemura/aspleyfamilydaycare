package com.ktiteng.entity;

import java.time.LocalDate;

public class PaymentSchedule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double amountInvoiced;
	double amountReceived;
	LocalDate billingStartDate;
	LocalDate billingEndDate;

	LocalDate dateReceived;
	boolean completed;
	boolean receiptIssued;
	Receipt receipt;
}
