package com.ktiteng.entity.account;

import java.util.List;

import com.ktiteng.entity.BaseEntity;

public class Bas extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BasQuarterly> basQuarterly;
	private double estimatedTax;
	
	public List<BasQuarterly> getBasQuarterly() {
		return basQuarterly;
	}

	public Bas setBasQuarterly(List<BasQuarterly> basQuarterly) {
		this.basQuarterly = basQuarterly;
		return this;
	}

	public double getEstimatedTax() {
		return estimatedTax;
	}

	public void setEstimatedTax(double estimatedTax) {
		this.estimatedTax = estimatedTax;
	}
}
