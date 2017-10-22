package com.ktiteng.entity.account;

import java.time.LocalDate;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class Transaction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final LocalDate date;
	private final String description;
	private final double amount;
	private final double balance;
	private boolean credit = false;

	public Transaction(LocalDate date, String description, double amount, double balance) {
		id(Utils.getId());
		this.date = date;
		this.description = description;
		this.amount = amount;
		this.balance = balance;
		if (Utils.isPositive(amount) && description.startsWith("DIRECT CREDIT")) {
			this.credit = true;
		}
		setGeneratedAt();
	}

	public LocalDate getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Transaction)) {
			return false;
		}
		Transaction other = (Transaction) obj;
		return date.equals(other.date) && description.equals(other.description)
				&& Double.compare(amount, other.amount) == 0 && Double.compare(balance, other.balance) == 0;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	@Override
	public String toString() {
		return String.join(" ", date.toString(), description, String.valueOf(amount), String.valueOf(balance));
	}
}
