package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.entity.Payable;

public class InitialPayment extends BaseEntity implements Payable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double deposit;
	private double depositRefund;
	private double enrollmentFee;
	private LocalDate depositPaidOn;
	private LocalDate depositRefundedOn;
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

	public InitialPayment setReceiptDeposit(Receipt receiptDeposit) {
		this.receiptDeposit = receiptDeposit;
		return this;
	}

	public Receipt getReceiptEnrollmentFee() {
		return receiptEnrollmentFee;
	}

	public InitialPayment setReceiptEnrollmentFee(Receipt receiptEnrollmentFee) {
		this.receiptEnrollmentFee = receiptEnrollmentFee;
		return this;
	}

	public double getDepositRefund() {
		return depositRefund;
	}

	public InitialPayment setDepositRefund(double depositRefund) {
		this.depositRefund = depositRefund;
		return this;
	}

	public LocalDate getDepositRefundedOn() {
		return depositRefundedOn;
	}

	public InitialPayment setDepositRefundedOn(LocalDate depositRefundedOn) {
		this.depositRefundedOn = depositRefundedOn;
		return this;
	}
}
