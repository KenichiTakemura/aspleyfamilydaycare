package com.ktiteng.entity;

import java.util.ArrayList;
import java.util.List;

import com.ktiteng.util.Utils;

public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String childId;
	private InitialPayment initialPayment;
	private List<PaymentSchedule> paymentSchedule;

	public Payment() {
		setId(Utils.getId());
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

}
