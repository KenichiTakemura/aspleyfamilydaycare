package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public abstract class Payable<T> extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taxInvoiceId;
	private double balanceDue;
	private double currentBalance;

	private double amountInvoiced;
	private double amountReceived;
	private LocalDate dateReceived;
	private boolean received;
	private boolean completed;
	private String receiptId;

	public Payable() {
		id(Utils.getId());
	}

	public double getBalanceDue() {
		return balanceDue;
	}

	public T setTaxInvoiceId(String taxInvoiceId) {
		this.taxInvoiceId = taxInvoiceId;
		return self();
	}

	public String getTaxInvoiceId() {
		return taxInvoiceId;
	}

	public T setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
		return self();
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public T setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
		return self();
	}

	public double getAmountInvoiced() {
		return amountInvoiced;
	}

	public T setAmountInvoiced(double amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
		setAmountReceived(amountInvoiced);
		setBalanceDue(NIL_BALANCE);
		return self();
	}

	public double getAmountReceived() {
		return amountReceived;
	}

	public T setAmountReceived(double amountReceived) {
		this.amountReceived = amountReceived;
		return self();
	}

	public LocalDate getDateReceived() {
		return dateReceived;
	}

	public T setDateReceived(LocalDate dateReceived) {
		this.dateReceived = dateReceived;
		return self();
	}

	public boolean isCompleted() {
		return completed;
	}

	public T setCompleted(boolean completed) {
		this.completed = completed;
		return self();
	}

	public String getReceiptId() {
		return receiptId;
	}

	public T setReceiptId(String receiptId) {
		this.receiptId = receiptId;
		return self();
	}

	public boolean isReceived() {
		return received;
	}

	public T setReceived(boolean received) {
		this.received = received;
		return self();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Payable)) {
			return false;
		}
		Payable<T> other = (Payable<T>) obj;
		return taxInvoiceId.equals(other.taxInvoiceId);
	}

	protected T self() {
		return (T) this;
	}

}
