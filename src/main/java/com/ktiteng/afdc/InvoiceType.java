package com.ktiteng.afdc;

public enum InvoiceType {
	WEEKS, DEPOSIT, ENROLLMENT;

	public static InvoiceType get(String value) {
		return InvoiceType.valueOf(value.toUpperCase());
	}
}
