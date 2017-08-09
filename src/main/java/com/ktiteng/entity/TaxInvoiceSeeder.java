package com.ktiteng.entity;

import java.util.concurrent.atomic.AtomicInteger;

public class TaxInvoiceSeeder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int INIT_VAL = 10000;
	private AtomicInteger val;

	public TaxInvoiceSeeder() {
		this.val.set(INIT_VAL);
	}

	public String nextVal() {
		return String.valueOf(val.incrementAndGet());
	}
}
