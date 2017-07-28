package com.ktiteng.entity;

import java.time.LocalDate;
import java.util.List;

public class Payment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	double deposit;
	double resigtrationFee;
	LocalDate depositPaidOn;
	LocalDate resigtrationFeePaidOn;
	List<PaymentSchedule> paymentSchedule;
}
