package com.ktiteng.entity;

import java.io.Serializable;

public abstract class BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private long generatedAt;
	public static double NIL_BALANCE = 0.0d;

	protected BaseEntity() {
	}

	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}

	public long getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt() {
		this.generatedAt = System.currentTimeMillis();
	}

}
