package com.ktiteng.entity.service;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Child extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;
	private String firstName;
	private String lastName;
	private String childNumber;
	private LocalDate startDate;
	private LocalDate lastDate;

	public Child() {
		setId(Utils.getId());
	}

	public Child(String firstName, String lastName, String childNumber, String parentId) {
		this();
		setFirstName(firstName);
		setLastName(lastName);
		setChildNumber(childNumber);
		setParentId(parentId);
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		if (!Utils.isNullOrBlank(startDate)) {
			this.startDate = LocalDate.parse(startDate);
		}
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		if (!Utils.isNullOrBlank(lastDate)) {
			this.lastDate = LocalDate.parse(lastDate);
		}
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	@JsonIgnore
	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public String getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(String childNumber) {
		this.childNumber = childNumber;
	}
}
