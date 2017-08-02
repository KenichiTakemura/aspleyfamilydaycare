package com.ktiteng.entity;

import java.time.LocalDate;
import java.util.List;

public class InitialPayment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private double deposit;
	private double resigtrationFee;
	private LocalDate depositPaidOn;
	private LocalDate resigtrationFeePaidOn;

	public double getDeposit() {
		return deposit;
	}

	public InitialPayment setDeposit(double deposit) {
		this.deposit = deposit;
		return this;
	}

	public double getResigtrationFee() {
		return resigtrationFee;
	}

	public InitialPayment setResigtrationFee(double resigtrationFee) {
		this.resigtrationFee = resigtrationFee;
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

	public LocalDate getResigtrationFeePaidOn() {
		return resigtrationFeePaidOn;
	}

	public InitialPayment setResigtrationFeePaidOn(String resigtrationFeePaidOn) {
		this.resigtrationFeePaidOn = LocalDate.parse(resigtrationFeePaidOn);
		return this;
	}

	public InitialPayment setResigtrationFeePaidOn(LocalDate resigtrationFeePaidOn) {
		this.resigtrationFeePaidOn = resigtrationFeePaidOn;
		return this;
	}

}
