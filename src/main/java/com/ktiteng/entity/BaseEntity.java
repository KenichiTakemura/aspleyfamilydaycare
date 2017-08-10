package com.ktiteng.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;

	protected BaseEntity() {

	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

}
