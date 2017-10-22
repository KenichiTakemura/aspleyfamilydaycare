package com.ktiteng.controller.account;

import java.io.IOException;
import java.time.LocalDate;

import com.ktiteng.entity.account.BankTransaction;
import com.ktiteng.entity.account.Banks;
import com.ktiteng.entity.account.BasQuarter;
import com.ktiteng.entity.account.BasQuarterly;
import com.ktiteng.entity.account.Expense;
import com.ktiteng.entity.account.Transaction;

public interface BasService {

	BasQuarterly getBasYearly(LocalDate year);

	BasQuarterly getBasQuarterly(BasQuarter quarter);

	BasQuarterly newBasQuarterly(BasQuarter quarter) throws IOException;
	
	void addExpense(Expense expense) throws Exception;
	
	BankTransaction loadBankTransaction(Banks bank, String downloadedFile) throws IOException;
	
	Transaction unrelated(Banks bank, Transaction transaction) throws IOException;
	
	Transaction related(Banks bank, Transaction transaction) throws IOException;
	
	
}
