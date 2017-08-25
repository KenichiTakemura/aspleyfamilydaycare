package com.ktiteng.entity.account;

import java.time.LocalDate;

public class Transaction {

	Retailers retailers;
	LocalDate purchasedOn;
	double price;
	double gst;
	double princeWithoutGst;
	String receiptLocation;
}
