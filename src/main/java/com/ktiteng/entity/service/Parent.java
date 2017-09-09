package com.ktiteng.entity.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

	public Parent(String firstName, String lastName, String phoneNumber, String emailAddress) {
		this();
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
		setEmailAddress(emailAddress);
	}

	public String getFirstName() {
		return firstName;
	}

	public Parent setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Parent setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Parent setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		return this;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public Parent setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		return this;
	}

	public BankDetail getBankDetail() {
		return bankDetail;
	}

	public Parent setBankDetail(BankDetail bankDetail) {
		this.bankDetail = bankDetail;
		return this;
	}

	@JsonIgnore
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Parent)) {
			return false;
		}
		Parent other = (Parent) obj;
		return getName().equals(other.getName());
	}

}
