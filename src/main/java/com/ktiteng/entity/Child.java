package com.ktiteng.entity;

import java.time.LocalDate;

import com.ktiteng.util.Utils;

public class Child extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String parentId;
	private String firstName;
	private String lastName;
	private LocalDate startDate;
	private LocalDate lastDate;
	private TimeCard timeCard;
	private Payment payment;

	public Child() {
		setId(Utils.getId());
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

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getLastDate() {
		return lastDate;
	}

	public void setLastDate(LocalDate lastDate) {
		this.lastDate = lastDate;
	}

	public TimeCard getTimeCard() {
		return timeCard;
	}

	public void setTimeCard(TimeCard timeCard) {
		this.timeCard = timeCard;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
