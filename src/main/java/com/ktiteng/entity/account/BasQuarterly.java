package com.ktiteng.entity.account;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class BasQuarterly extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BasQuarter quarter;
	private String income;
	private String expense;
	private String loss;
	private String decuction;
	private String gstClaimable;
	private String basQuarterlyDetailsId;
	
	public BasQuarterly(BasQuarter quarter) {
		id(Utils.getId());
		this.quarter = quarter;
	}
	
	public BasQuarter getQuarter() {
		return quarter;
	}

	public String getBasQuarterlyDetailsId() {
		return basQuarterlyDetailsId;
	}

	public void setBasQuarterlyDetailsId(String basQuarterlyDetailsId) {
		this.basQuarterlyDetailsId = basQuarterlyDetailsId;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getExpense() {
		return expense;
	}

	public void setExpense(String expense) {
		this.expense = expense;
	}

	public String getLoss() {
		return loss;
	}

	public void setLoss(String loss) {
		this.loss = loss;
	}

	public String getDecuction() {
		return decuction;
	}

	public void setDecuction(String decuction) {
		this.decuction = decuction;
	}

	public String getGstClaimable() {
		return gstClaimable;
	}

	public void setGstClaimable(String gstClaimable) {
		this.gstClaimable = gstClaimable;
	}


}
