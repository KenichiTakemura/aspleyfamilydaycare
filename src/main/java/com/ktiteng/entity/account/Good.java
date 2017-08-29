package com.ktiteng.entity.account;

import com.ktiteng.entity.BaseEntity;

public class Good extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double unitPrice;
	private int quantity;
	private double price;
	private double gst;
	private double priceExGst;

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getGst() {
		return gst;
	}

	public void setGst(double gst) {
		this.gst = gst;
	}

	public double getPriceExGst() {
		return priceExGst;
	}

	public void setPriceExGst(double priceExGst) {
		this.priceExGst = priceExGst;
	}

}
