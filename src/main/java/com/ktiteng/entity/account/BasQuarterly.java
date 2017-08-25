package com.ktiteng.entity.account;

import java.time.LocalDate;

import com.ktiteng.entity.BaseEntity;

public class BasQuarterly extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDate quarter;
	private double income;
	private double expense;
	private double loss;
	private double gstPaid;
	private double gstClaimed;
	private String basQuarterlyDetailsId;
	
	public LocalDate getQuarter() {
		return quarter;
	}

	public void setQuarter(LocalDate quarter) {
		this.quarter = quarter;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpense() {
		return expense;
	}

	public void setExpense(double expense) {
		this.expense = expense;
	}

	public double getLoss() {
		return loss;
	}

	public void setLoss(double loss) {
		this.loss = loss;
	}

	public double getGstPaid() {
		return gstPaid;
	}

	public void setGstPaid(double gstPaid) {
		this.gstPaid = gstPaid;
	}

	public double getGstClaimed() {
		return gstClaimed;
	}

	public void setGstClaimed(double gstClaimed) {
		this.gstClaimed = gstClaimed;
	}

	public String getBasQuarterlyDetailsId() {
		return basQuarterlyDetailsId;
	}

	public void setBasQuarterlyDetailsId(String basQuarterlyDetailsId) {
		this.basQuarterlyDetailsId = basQuarterlyDetailsId;
	}
}
