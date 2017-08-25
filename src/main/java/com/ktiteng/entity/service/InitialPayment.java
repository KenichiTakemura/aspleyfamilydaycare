package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.ktiteng.entity.BaseEntity;

public class InitialPayment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double deposit;
	private double enrollmentFee;
	private LocalDate depositPaidOn;
	private LocalDate enrollmentFeePaidOn;
	private Receipt receiptDeposit;
	private Receipt receiptEnrollmentFee;

	public double getDeposit() {
		return deposit;
	}

	public InitialPayment setDeposit(double deposit) {
		this.deposit = deposit;
		return this;
	}

	public LocalDate getDepositPaidOn() {
		return depositPaidOn;
	}

	public InitialPayment setDepositPaidOn(String depositPaidOn) {
		this.depositPaidOn = LocalDate.parse(depositPaidOn);
		return this;
	}

	public InitialPayment setDepositPaidOn(LocalDate depositPaidOn) {
		this.depositPaidOn = depositPaidOn;
		return this;
	}

	public LocalDate getEnrollmentFeePaidOn() {
		return enrollmentFeePaidOn;
	}

	public InitialPayment setEnrollmentFeePaidOn(String enrollmentFeePaidOn) {
		this.enrollmentFeePaidOn = LocalDate.parse(enrollmentFeePaidOn);
		return this;
	}

	public InitialPayment setEnrollmentFeePaidOn(LocalDate resigtrationFeePaidOn) {
		this.enrollmentFeePaidOn = resigtrationFeePaidOn;
		return this;
	}

	public double getEnrollmentFee() {
		return enrollmentFee;
	}

	public InitialPayment setEnrollmentFee(double enrollmentFee) {
		this.enrollmentFee = enrollmentFee;
		return this;
	}

	public Receipt getReceiptDeposit() {
		return receiptDeposit;
	}

	public void setReceiptDeposit(Receipt receiptDeposit) {
		this.receiptDeposit = receiptDeposit;
	}

	public Receipt getReceiptEnrollmentFee() {
		return receiptEnrollmentFee;
	}

	public void setReceiptEnrollmentFee(Receipt receiptEnrollmentFee) {
		this.receiptEnrollmentFee = receiptEnrollmentFee;
	}

}
