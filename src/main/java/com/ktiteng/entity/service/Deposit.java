package com.ktiteng.entity.service;

import java.time.LocalDate;

public class Deposit extends Payable<Deposit> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double refund;
	private LocalDate dateRefunded;
	private boolean refunded;

	public double getRefund() {
		return refund;
	}

	public Deposit setRefund(double refund) {
		this.refund = refund;
		return self();
	}

	public LocalDate getDateRefunded() {
		return dateRefunded;
	}

	public Deposit setDateRefunded(LocalDate dateRefunded) {
		this.dateRefunded = dateRefunded;
		return self();
	}

	public boolean isRefunded() {
		return refunded;
	}

	public Deposit setRefunded(boolean refunded) {
		this.refunded = refunded;
		return self();
	}
}
