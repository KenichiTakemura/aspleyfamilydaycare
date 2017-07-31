package com.ktiteng.entity;

import java.util.List;

public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String childId;
	private InitialPayment initialPayment;
	private List<PaymentSchedule> paymentSchedule;

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public InitialPayment getInitialPayment() {
		return initialPayment;
	}

	public void setInitialPayment(InitialPayment initialPayment) {
		this.initialPayment = initialPayment;
	}

	public List<PaymentSchedule> getPaymentSchedule() {
		return paymentSchedule;
	}

	public void setPaymentSchedule(List<PaymentSchedule> paymentSchedule) {
		this.paymentSchedule = paymentSchedule;
	}

}
