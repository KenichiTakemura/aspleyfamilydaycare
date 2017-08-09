package com.ktiteng.entity;

import java.time.LocalDate;

public class TaxInvoice extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate generatedAt;
	private String location;
	
	public TaxInvoice() {
		
	}

	public LocalDate getGeneratedAt() {
		return generatedAt;
	}

	public TaxInvoice setGeneratedAt(LocalDate generatedAt) {
		this.generatedAt = generatedAt;
		return this;
	}

	public String getLocation() {
		return location;
	}

	public TaxInvoice setLocation(String location) {
		this.location = location;
		return this;
	}

}
