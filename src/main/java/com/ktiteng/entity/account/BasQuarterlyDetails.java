package com.ktiteng.entity.account;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class BasQuarterlyDetails extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String basQuarterId;
	private List<Expense> expenses;
	private BankTransaction bankTransaction;
	private transient BigDecimal expense;
	private transient BigDecimal loss;
	private transient BigDecimal decuction;

	private transient BigDecimal gstClaimable;

	public BasQuarterlyDetails(String basQuarterId) {
		id(Utils.getId());
		this.basQuarterId = basQuarterId;
		expenses = Lists.newArrayList();
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public BasQuarterlyDetails addExpenses(Expense expense) {
		if (!expenses.contains(expense)) {
			expenses.add(expense);
			this.expense = expenses.stream().map(e -> e.getPriceTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			this.gstClaimable = expenses.stream().map(e -> e.getGstTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
			this.decuction = expenses.stream().map(e -> e.getPriceExGstTotal()).reduce(BigDecimal.ZERO,
					BigDecimal::add);
		}
		return this;
	}

	public BasQuarterlyDetails setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
		return this;
	}

	public BankTransaction getBankTransaction() {
		return bankTransaction;
	}

	public BasQuarterlyDetails setBankTransaction(BankTransaction bankTransaction) {
		this.bankTransaction = bankTransaction;
		return this;
	}

	public String getBasQuarterId() {
		return basQuarterId;
	}

	public BigDecimal getExpense() {
		return expense;
	}

	public BigDecimal getLoss() {
		return loss;
	}

	public BigDecimal getDecuction() {
		return decuction;
	}

	public BigDecimal getGstClaimable() {
		return gstClaimable;
	}
}
