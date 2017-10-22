package com.ktiteng.controller.bean.account;

import static com.ktiteng.util.Utils.toDate;
import static com.ktiteng.util.Utils.df;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import com.ktiteng.controller.account.BasService;
import com.ktiteng.controller.bean.BaseController;
import com.ktiteng.entity.account.BankTransaction;
import com.ktiteng.entity.account.Banks;
import com.ktiteng.entity.account.BasQuarter;
import com.ktiteng.entity.account.BasQuarterly;
import com.ktiteng.entity.account.BasQuarterlyDetails;
import com.ktiteng.entity.account.Expense;
import com.ktiteng.entity.account.Transaction;

@Default
@ApplicationScoped
public class BasManager extends BaseController implements BasService {

	@PostConstruct
	public void init() {
		log.info("produced");
	}

	@Override
	public BasQuarterly getBasYearly(LocalDate year) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BasQuarterly getBasQuarterly(BasQuarter quarter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankTransaction loadBankTransaction(Banks bank, String downloadedFile) throws IOException {
		Stream<String> lines = Files.lines(Paths.get(downloadedFile));
		BankTransaction bankTransaction = (BankTransaction) em.find(BankTransaction.class,
				new String[] { bank.toString() });
		if (bankTransaction == null) {
			bankTransaction = new BankTransaction(bank);
		}
		BankTransaction thisTransaction = new BankTransaction(bank);
		lines.forEach(line -> {
			if (!line.startsWith("Account")) {
				String[] row = line.split("\t");
				log.trace("row {}", Arrays.deepToString(row));
				String amount = row[2].replaceAll("\\$", "").replaceAll(",", "");
				String balance = row[3].replaceAll("\\$", "").replaceAll(",", "");
				thisTransaction.addTransaction(new Transaction(toDate(row[0]), row[1], Double.parseDouble(amount),
						Double.parseDouble(balance)));
			}
		});
		lines.close();
		bankTransaction.addAll(thisTransaction);
		em.save(bankTransaction.setGeneratedAt());
		return bankTransaction;
	}

	@Override
	public Transaction unrelated(Banks bank, Transaction transaction) throws IOException {
		BankTransaction bankTransaction = (BankTransaction) em.find(BankTransaction.class,
				new String[] { bank.toString() });
		Transaction t = null;
		if (bankTransaction != null) {
			t = bankTransaction.findTransaction(transaction);
			if (t != null) {
				t.setCredit(false);
				em.save(bankTransaction);
			}
		}
		return t;
	}

	@Override
	public Transaction related(Banks bank, Transaction transaction) throws IOException {
		BankTransaction bankTransaction = (BankTransaction) em.find(BankTransaction.class,
				new String[] { bank.toString() });
		Transaction t = null;
		if (bankTransaction != null) {
			t = bankTransaction.findTransaction(transaction);
			if (t != null) {
				t.setCredit(true);
				em.save(bankTransaction);
			}
		}
		return t;
	}

	@Override
	public BasQuarterly newBasQuarterly(BasQuarter quarter) throws IOException {
		BasQuarterly basQuarterly = new BasQuarterly(quarter);
		BankTransaction bankTransaction = (BankTransaction) em.find(BankTransaction.class,
				new String[] { Banks.SUNCORP.toString() });
		if (bankTransaction != null) {
			BasQuarterlyDetails basQuarterlyDetails = new BasQuarterlyDetails(basQuarterly.id())
					.setBankTransaction(bankTransaction.build(quarter));
			basQuarterly.setBasQuarterlyDetailsId(basQuarterlyDetails.id());
			basQuarterly.setGeneratedAt();
			basQuarterly.setIncome(df(bankTransaction.incomeFQ(quarter)));
			em.save(basQuarterly);
			basQuarterlyDetails.setGeneratedAt();
			em.save(basQuarterlyDetails);
		}
		return basQuarterly;
	}

	@Override
	public void addExpense(Expense expense) throws Exception {
		BasQuarter quarter = BasQuarter.findByDate(expense.getPurchasedOn());
		if (quarter == null) {
			throw new Exception("Date is out of range. " + expense.getPurchasedOn());
		}
		BasQuarterly basQuarterly = (BasQuarterly) em.find(BasQuarterly.class, new Object[]{quarter});
		BasQuarterlyDetails basQuarterlyDetails = (BasQuarterlyDetails) em.find(BasQuarterlyDetails.class, new Object[]{basQuarterly.getBasQuarterlyDetailsId()});
		basQuarterlyDetails.addExpenses(expense);
		em.save(basQuarterlyDetails);
//		basQuarterly.setExpense(expense);
//		basQuarterly.setGstClaimable(gstClaimable);
//		basQuarterly.set
	}

}
