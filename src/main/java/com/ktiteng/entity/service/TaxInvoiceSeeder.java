package com.ktiteng.entity.service;

import com.ktiteng.entity.BaseEntity;

public class TaxInvoiceSeeder extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PREFIX = "AFDC-";
	private static final long INIT_VAL = 10000;
	private long val;

	public TaxInvoiceSeeder() {
		this.val = INIT_VAL;
	}

	public long getVal() {
		return val;
	}

	public void setVal(long val) {
		this.val = val;
	}

	public synchronized String nextVal() {
		return PREFIX + String.valueOf(++val);
	}
}
