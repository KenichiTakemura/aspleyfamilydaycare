package com.ktiteng.entity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String childId;
	private Deposit deposit;
	private EnrollmentFee enrollmentFee;
	private double balanceDue;
	private List<PaymentSchedule> paymentSchedules;

	public Payment() {
		id(Utils.getId());
		paymentSchedules = new ArrayList<>();
	}

	public String getChildId() {
		return childId;
	}

	public Payment setChildId(String childId) {
		this.childId = childId;
		return this;
	}

	public PaymentSchedule getPaymentSchedule(String paymentScheduleId) {
		Optional<PaymentSchedule> ops = paymentSchedules.stream()
				.filter(ps -> ps.id().equals(paymentScheduleId)).findFirst();
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
			this.paymentSchedules = Lists.newArrayList();
		}
		this.paymentSchedules.add(paymentSchedule);
		return paymentSchedule;
	}

	public PaymentSchedule updatePaymentSchedule(PaymentSchedule paymentSchedule) {
		if (this.paymentSchedules == null) {
			throw new IllegalStateException("Not exists. Use add.");
		}
		this.paymentSchedules.removeIf(p -> p!= null && p.id().equals(paymentSchedule.id()));
		this.paymentSchedules.add(paymentSchedule);
		return paymentSchedule;
	}
	
	public double getBalanceDue() {
		return balanceDue;
	}

	public Payment setBalanceDue(double balanceDue) {
		this.balanceDue = balanceDue;
		return this;
	}

	public Deposit getDeposit() {
		return deposit;
	}

	public Payment setDeposit(Deposit deposit) {
		this.deposit = deposit;
		return this;
	}

	public EnrollmentFee getEnrollmentFee() {
		return enrollmentFee;
	}

	public Payment setEnrollmentFee(EnrollmentFee enrollmentFee) {
		this.enrollmentFee = enrollmentFee;
		return this;
	}

}
