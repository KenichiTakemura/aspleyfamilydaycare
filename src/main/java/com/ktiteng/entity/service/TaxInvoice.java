package com.ktiteng.entity.service;

import java.time.LocalDateTime;

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
	private LocalDateTime sentAt;

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

	public LocalDateTime getSentAt() {
		return sentAt;
	}

	public TaxInvoice setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
		return this;
	}

	public String getPayableId() {
		return payableId;
	}

	public TaxInvoice setPayableId(String payableId) {
		this.payableId = payableId;
		return this;
	}

}
