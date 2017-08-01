package com.ktiteng.entity;

public class BankDetail extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bsb;
	private String account;
	private String accountName;

	public String getBsb() {
		return bsb;
	}

	public BankDetail setBsb(String bsb) {
		this.bsb = bsb;
		return this;
	}

	public String getAccount() {
		return account;
	}

	public BankDetail setAccount(String account) {
		this.account = account;
		return this;
	}

	public String getAccountName() {
		return accountName;
	}

	public BankDetail setAccountName(String accountName) {
		this.accountName = accountName;
		return this;
	}
}
