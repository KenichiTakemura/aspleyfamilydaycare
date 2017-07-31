package com.ktiteng.entity;

import java.time.LocalDate;

public class Receipt extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate generatedAt;
	private String receiptLocation;

	public LocalDate getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(LocalDate generatedAt) {
		this.generatedAt = generatedAt;
	}

	public String getReceiptLocation() {
		return receiptLocation;
	}

	public void setReceiptLocation(String receiptLocation) {
		this.receiptLocation = receiptLocation;
	}
}
