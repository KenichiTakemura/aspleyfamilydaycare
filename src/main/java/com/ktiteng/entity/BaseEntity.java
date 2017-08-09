package com.ktiteng.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	protected BaseEntity() {

	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	protected LocalDate toDate(String date) {
		return LocalDate.parse(date);
	}

	protected String toDateStr(LocalDate date) {
		return formatter.format(date);
	}

}
