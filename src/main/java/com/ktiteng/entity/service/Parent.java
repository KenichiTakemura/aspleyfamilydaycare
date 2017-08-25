package com.ktiteng.entity.service;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Parent extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private BankDetail bankDetail;

	public Parent() {
		setId(Utils.getId());
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public BankDetail getBankDetail() {
		return bankDetail;
	}

	public void setBankDetail(BankDetail bankDetail) {
		this.bankDetail = bankDetail;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}
}
