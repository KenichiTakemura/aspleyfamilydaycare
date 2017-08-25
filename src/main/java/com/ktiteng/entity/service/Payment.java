package com.ktiteng.entity.service;

import java.util.ArrayList;
import java.util.List;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String childId;
	private InitialPayment initialPayment;
	private double balanceDue;
	private List<PaymentSchedule> paymentSchedule;

	public Payment() {
		setId(Utils.getId());
		paymentSchedule = new ArrayList<>();
	}

	public String getChildId() {
		return childId;
	}

	public Payment setChildId(String childId) {
		this.childId = childId;
		return this;
	}

	public InitialPayment getInitialPayment() {
		return initialPayment;
	}

	public Payment setInitialPayment(InitialPayment initialPayment) {
		this.initialPayment = initialPayment;
		return this;
	}

	public List<PaymentSchedule> getPaymentSchedule() {
		return paymentSchedule;
	}

	public Payment setPaymentSchedule(List<PaymentSchedule> paymentSchedule) {
		this.paymentSchedule = paymentSchedule;
		return this;
	}

	public PaymentSchedule addPaymentSchedule(PaymentSchedule paymentSchedule) {
		if (this.paymentSchedule == null) {
			this.paymentSchedule = new ArrayList<>();
		}
		this.paymentSchedule.add(paymentSchedule);
		return paymentSchedule;
	}

	public PaymentSchedule updatePaymentSchedule(PaymentSchedule paymentSchedule) {
		if (this.paymentSchedule == null) {
			throw new IllegalStateException("Not exists. Use add.");
		}
		this.paymentSchedule.removeIf(p -> p!= null && p.getId().equals(paymentSchedule.getId()));
		this.paymentSchedule.add(paymentSchedule);
		return paymentSchedule;
	}
	
	public double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}

}
