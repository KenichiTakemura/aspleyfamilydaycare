package com.ktiteng.entity;

import java.time.LocalDate;

public class PaymentSchedule extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double balanceDue;
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

	public PaymentSchedule setAmountInvoiced(double amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
		return this;
	}

	public double getAmountReceived() {
		return amountReceived;
	}

	public PaymentSchedule setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
		return this;
	}

	public LocalDate getBillingStartDate() {
		return billingStartDate;
	}
	
	public PaymentSchedule setBillingStartDate(LocalDate billingStartDate) {
		this.billingStartDate = billingStartDate;
		return this;
	}

	public LocalDate getBillingEndDate() {
		return billingEndDate;
	}

	public PaymentSchedule setBillingEndDate(LocalDate billingEndDate) {
		this.billingEndDate = billingEndDate;
		return this;
	}

	public LocalDate getDateReceived() {
		return dateReceived;
	}

	public PaymentSchedule setDateReceived(LocalDate dateReceived) {
		this.dateReceived = dateReceived;
		return this;
	}

	public boolean isCompleted() {
		return completed;
	}

	public PaymentSchedule setCompleted(boolean completed) {
		this.completed = completed;
		return this;
	}

	public boolean isReceiptIssued() {
		return receiptIssued;
	}

	public PaymentSchedule setReceiptIssued(boolean receiptIssued) {
		this.receiptIssued = receiptIssued;
		return this;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public PaymentSchedule setReceipt(Receipt receipt) {
		this.receipt = receipt;
		return this;
	}

	public double getBalanceDue() {
		return balanceDue;
	}


	public PaymentSchedule setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
		return this;
	}

}
