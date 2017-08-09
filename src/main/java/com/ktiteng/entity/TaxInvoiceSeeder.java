package com.ktiteng.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class TaxInvoiceSeeder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "AFDC#";
	private static final int INIT_VAL = 10000;
	private AtomicInteger val;

	public TaxInvoiceSeeder() {
		this.val = new AtomicInteger(INIT_VAL);
	}

	public String nextVal() {
		return PREFIX + String.valueOf(val.incrementAndGet());
	}
}
