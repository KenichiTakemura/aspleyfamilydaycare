package com.ktiteng.entity.service;

public class Receipt extends TaxInvoice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String taxInvoiceId;
	private String name;

	public Receipt() {
	}

	public String getTaxInvoiceId() {
		return taxInvoiceId;
	}

	public Receipt setTaxInvoiceId(String taxInvoiceId) {
		this.taxInvoiceId = taxInvoiceId;
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
