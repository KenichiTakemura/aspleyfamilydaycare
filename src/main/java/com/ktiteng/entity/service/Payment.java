package com.ktiteng.entity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	private List<PaymentSchedule> paymentSchedules;

	public Payment() {
		setId(Utils.getId());
		paymentSchedules = new ArrayList<>();
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

	public PaymentSchedule getPaymentSchedule(String paymentScheduleId) {
		Optional<PaymentSchedule> ops = paymentSchedules.stream()
				.filter(ps -> ps.getId().equals(paymentScheduleId)).findFirst();
		return ops.isPresent() ? ops.get() : null;
	}

	public List<PaymentSchedule> getPaymentSchedules() {
		return paymentSchedules;
	}

	public Payment setPaymentSchedules(List<PaymentSchedule> paymentSchedules) {
		this.paymentSchedules = paymentSchedules;
		return this;
	}

	public PaymentSchedule addPaymentSchedule(PaymentSchedule paymentSchedule) {
		if (this.paymentSchedules == null) {
			this.paymentSchedules = new ArrayList<>();
		}
		this.paymentSchedules.add(paymentSchedule);
		return paymentSchedule;
	}

	public PaymentSchedule updatePaymentSchedule(PaymentSchedule paymentSchedule) {
		if (this.paymentSchedules == null) {
			throw new IllegalStateException("Not exists. Use add.");
		}
		this.paymentSchedules.removeIf(p -> p!= null && p.getId().equals(paymentSchedule.getId()));
		this.paymentSchedules.add(paymentSchedule);
		return paymentSchedule;
	}
	
	public double getBalanceDue() {
		return balanceDue;
	}

	public void setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
	}

}
