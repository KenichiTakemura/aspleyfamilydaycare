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

	public void setBsb(String bsb) {
		this.bsb = bsb;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
}
