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

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public double getResigtrationFee() {
		return resigtrationFee;
	}

	public void setResigtrationFee(double resigtrationFee) {
		this.resigtrationFee = resigtrationFee;
	}

	public LocalDate getDepositPaidOn() {
		return depositPaidOn;
	}

	public void setDepositPaidOn(String depositPaidOn) {
		this.depositPaidOn = LocalDate.parse(depositPaidOn);
	}

	public void setDepositPaidOn(LocalDate depositPaidOn) {
		this.depositPaidOn = depositPaidOn;
	}

	public LocalDate getResigtrationFeePaidOn() {
		return resigtrationFeePaidOn;
	}

	public void setResigtrationFeePaidOn(String resigtrationFeePaidOn) {
		this.resigtrationFeePaidOn = LocalDate.parse(resigtrationFeePaidOn);
	}

	public void setResigtrationFeePaidOn(LocalDate resigtrationFeePaidOn) {
		this.resigtrationFeePaidOn = resigtrationFeePaidOn;
	}

}
