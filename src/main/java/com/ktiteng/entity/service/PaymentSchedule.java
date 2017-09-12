package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.ktiteng.controller.account.BillingWeek;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Payable;
import com.ktiteng.util.Utils;

public class PaymentSchedule extends BaseEntity implements Payable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double balanceDue;
	private double currentBalance;
	private double amountInvoiced;
	private double amountReceived;
	private LocalDate billingStartDate;
	private LocalDate billingEndDate;

	private LocalDate dateReceived;
	private boolean received;
	private boolean completed;
	private Receipt receipt;

	public PaymentSchedule() {
		setId(Utils.getId());
	}

	public double getAmountInvoiced() {
		return amountInvoiced;
	}

	public PaymentSchedule setAmountInvoiced(double amountInvoiced) {
		this.amountInvoiced = amountInvoiced;
		setAmountReceived(amountInvoiced);
		setBalanceDue(NIL_BALANCE);
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
		setBillingEndDate(billingStartDate.plusDays(BillingWeek.numberOfDays));
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

	public double getCurrentBalance() {
		return currentBalance;
	}

	public PaymentSchedule setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof PaymentSchedule)) {
			return false;
		}
		PaymentSchedule other = (PaymentSchedule) obj;
		return billingStartDate.equals(other.billingStartDate) &&
				billingEndDate.equals(other.billingEndDate);
	}

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }
}
