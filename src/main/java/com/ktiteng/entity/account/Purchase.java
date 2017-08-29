package com.ktiteng.entity.account;

import java.time.LocalDate;
import java.util.List;

import com.ktiteng.entity.BaseEntity;

public class Purchase extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Retailers retailers;
	LocalDate purchasedOn;
	List<Good> goods;
	String receiptLocation;
}
