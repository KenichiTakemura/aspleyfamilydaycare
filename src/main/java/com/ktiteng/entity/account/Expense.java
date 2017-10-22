package com.ktiteng.entity.account;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Expense extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Retailers retailers;
	private final LocalDate purchasedOn;
	private List<Good> goods;
	private final String receiptLocation;
	private BigDecimal priceTotal;
	private BigDecimal gstTotal;
	private BigDecimal priceExGstTotal;

	Expense(Retailers retailers, LocalDate purchasedOn, List<Good> goods, String receiptLocation) {
		super();
		this.retailers = retailers;
		this.purchasedOn = purchasedOn;
		this.goods = goods;
		this.receiptLocation = receiptLocation;
		setGeneratedAt();
		if (!goods.isEmpty()) {
			calc();
		}
	}

	void calc() {
		this.priceTotal = goods.stream().map(g -> g.getPrice()).reduce(BigDecimal::add).get();
		this.gstTotal = goods.stream().map(g -> g.getGst()).reduce(BigDecimal.ZERO, BigDecimal::add);
		this.priceExGstTotal = goods.stream().map(g -> g.getPriceExGst()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public static Expense of(Retailers retailers, String purchasedOn, List<Good> goods, String receiptLocation) {
		return new Expense(retailers, Utils.toDate(purchasedOn), goods, receiptLocation);
	}

	public static Expense of(Retailers retailers, String purchasedOn, List<Good> goods) {
		return new Expense(retailers, Utils.toDate(purchasedOn), goods, null);
	}

	public static Expense of(Retailers retailers, String purchasedOn) {
		return new Expense(retailers, Utils.toDate(purchasedOn), Lists.newArrayList(), null);
	}
	
	public Retailers getRetailers() {
		return retailers;
	}

	public LocalDate getPurchasedOn() {
		return purchasedOn;
	}

	public List<Good> getGoods() {
		return goods;
	}

	public void addGoods(Good good) {
		if (!goods.contains(good)) {
			goods.add(good);
			calc();
		}
	}
	
	public String getReceiptLocation() {
		return receiptLocation;
	}

	public BigDecimal getPriceTotal() {
		return priceTotal;
	}

	public BigDecimal getGstTotal() {
		return gstTotal;
	}

	public BigDecimal getPriceExGstTotal() {
		return priceExGstTotal;
	}

}
