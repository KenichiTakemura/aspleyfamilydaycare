package com.ktiteng.entity.account;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class BankTransaction extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Banks banks;

	private List<Transaction> transactions;

	public BankTransaction(Banks banks) {
		this.banks = banks;
		id(Utils.getId());
		transactions = Lists.newArrayList();
	}

	public BankTransaction addTransaction(Transaction transaction) {
		if (!transactions.contains(transaction)) {
			transactions.add(transaction);
		}
		return this;
	}

	BankTransaction(Banks banks, List<Transaction> transactions) {
		this(banks);
		this.transactions.addAll(transactions);
	}

	public BankTransaction addAll(BankTransaction other) {
		other.transactions.stream().forEach(this::addTransaction);
		Collections.sort(transactions, new Comparator<Transaction>() {

			@Override
			public int compare(Transaction t1, Transaction t2) {
				return t1.getDate().compareTo(t2.getDate());
			}

		});
		return this;
	}

	public Transaction findTransaction(Transaction transaction) {
		return transactions.stream().filter(t -> t.equals(transaction)).findFirst().orElse(null);
	}

	public int transactionCount() {
		return transactions.size();
	}

	public double incomeFQ(BasQuarter quarter) {
		return transactions.stream()
				.filter(t -> quarter.isWithin(t.getDate()))
				.filter(Transaction::isCredit).mapToDouble(t -> t.getAmount()).sum();
	}

	List<Transaction> transactionFQ(BasQuarter quarter) {
		return transactions.stream()
				.filter(t -> quarter.isWithin(t.getDate()))
				.collect(Collectors.toList());
	}

	public BankTransaction build(BasQuarter quarter) {
		BankTransaction transaction = new BankTransaction(this.banks, transactionFQ(quarter));
		transaction.setGeneratedAt();
		return transaction;
	}

	public double incomeFY(int year) {
		return 0;
	}

	public Banks getBanks() {
		return banks;
	}

}
