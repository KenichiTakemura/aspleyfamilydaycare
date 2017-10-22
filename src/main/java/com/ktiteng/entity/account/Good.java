package com.ktiteng.entity.account;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.ktiteng.entity.BaseEntity;

public class Good extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final BigDecimal ELEVEN = BigDecimal.valueOf(11.0);
	private final String name;
	private final BigDecimal unitPrice;
	private final int quantity;
	private final BigDecimal price;
	private final BigDecimal gst;
	private final boolean gstFree;
	private final BigDecimal priceExGst;

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getGst() {
		return gst;
	}

	public BigDecimal getPriceExGst() {
		return priceExGst;
	}

	public static Good of(String name, double unitPrice, int quantity, boolean gstFree) {
		BigDecimal uPrice = BigDecimal.valueOf(unitPrice);
		BigDecimal price = uPrice.multiply(BigDecimal.valueOf(quantity));
		BigDecimal gst = !gstFree ? price.divide(ELEVEN, 2, RoundingMode.HALF_DOWN) : BigDecimal.ZERO;
		BigDecimal priceExGst = price.subtract(gst);
		return new Good(name, uPrice, quantity, price, gst, gstFree, priceExGst);
	}

	public static Good of(String name, double unitPrice) {
		return of(name, unitPrice, 1, false);
	}

	public static Good of(String name, double unitPrice, int quantity) {
		return of(name, unitPrice, quantity, false);
	}

	Good(String name, BigDecimal unitPrice, int quantity, BigDecimal price, BigDecimal gst, boolean gstFree, BigDecimal priceExGst) {
		super();
		this.name = name;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
		this.price = price;
		this.gst = gst;
		this.gstFree = gstFree;
		this.priceExGst = priceExGst;
		setGeneratedAt();
	}

	public boolean isGstFree() {
		return gstFree;
	}

}
