package com.ktiteng.entity;

import java.time.LocalDate;

public class TaxInvoice extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String location;
	
	public TaxInvoice() {
	}

	public String getLocation() {
		return location;
	}

	public TaxInvoice setLocation(String location) {
		this.location = location;
		return this;
	}

}
