package com.ktiteng.entity;

public class Receipt extends TaxInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taxInvoiceId;

	public Receipt() {

	}

	public String getTaxInvoiceId() {
		return taxInvoiceId;
	}

	public Receipt setTaxInvoiceId(String taxInvoiceId) {
		this.taxInvoiceId = taxInvoiceId;
		return this;
	}
}
