package com.ktiteng.entity;

import java.time.LocalDate;

public class PaymentSchedule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double amountInvoiced;
	private double amountReceived;
	private LocalDate billingStartDate;
	private LocalDate billingEndDate;

	private LocalDate dateReceived;
	private boolean completed;
	private boolean receiptIssued;
	private Receipt receipt;

	public double getAmountInvoiced() {
		return amountInvoiced;
	}

	public void setAmountInvoiced(double amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
	}

	public double getAmountReceived() {
		return amountReceived;
	}

	public void setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
	}

	public LocalDate getBillingStartDate() {
		return billingStartDate;
	}

	public void setBillingStartDate(LocalDate billingStartDate) {
		this.billingStartDate = billingStartDate;
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public void setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public LocalDate getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(LocalDate dateReceived) {
		this.dateReceived = dateReceived;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isReceiptIssued() {
		return receiptIssued;
	}

	public void setReceiptIssued(boolean receiptIssued) {
		this.receiptIssued = receiptIssued;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

}
