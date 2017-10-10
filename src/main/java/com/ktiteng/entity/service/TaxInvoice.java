package com.ktiteng.entity.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ktiteng.afdc.InvoiceType;
import com.ktiteng.entity.BaseEntity;

public class TaxInvoice extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String payableId;
	private String location;
	private boolean issued;
	private boolean sent;
	private InvoiceType type;
	private LocalDate dateIssued;
	private LocalDate dateSent;
	private String sentTo;

	public TaxInvoice() {
	}

	public String getLocation() {
		return location;
	}

	public TaxInvoice setLocation(String location) {
		this.location = location;
		return this;
	}

	public boolean isIssued() {
		return issued;
	}

	public TaxInvoice setIssued(boolean issued) {
		this.issued = issued;
		return this;
	}

	public boolean isSent() {
		return sent;
	}

	public TaxInvoice setSent(boolean sent) {
		this.sent = sent;
		return this;
	}

	public String getPayableId() {
		return payableId;
	}

	public TaxInvoice setPayableId(String payableId) {
		this.payableId = payableId;
		return this;
	}

	public InvoiceType getType() {
		return type;
	}

	public TaxInvoice setType(InvoiceType type) {
		this.type = type;
		return this;
	}

	public LocalDate getDateIssued() {
		return dateIssued;
	}

	public TaxInvoice setDateIssued(LocalDate dateIssued) {
		this.dateIssued = dateIssued;
		return this;
	}

	public LocalDate getDateSent() {
		return dateSent;
	}

	public TaxInvoice setDateSent(LocalDate dateSent) {
		this.dateSent = dateSent;
		return this;
	}

	public String getSentTo() {
		return sentTo;
	}

	public TaxInvoice setSentTo(String sentTo) {
		this.sentTo = sentTo;
		return this;
	}

}
