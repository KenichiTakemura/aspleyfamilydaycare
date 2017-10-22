package com.ktiteng.controller.bean.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;

import javax.inject.Inject;

import com.ktiteng.entity.account.BankTransaction;
import com.ktiteng.entity.account.Banks;
import com.ktiteng.entity.account.BasQuarter;
import com.ktiteng.entity.account.BasQuarterly;
import com.ktiteng.entity.account.Expense;
import com.ktiteng.entity.account.Good;
import com.ktiteng.entity.account.Retailers;
import com.ktiteng.entity.account.Transaction;
import com.ktiteng.util.Utils;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.ktiteng.arquillian.ArquillianUnitTest;
import com.ktiteng.controller.account.BasService;

public class BasManagerTest extends ArquillianUnitTest {

	@Inject
	BasService basService;

	@Test
	public void newBasQuarterly() throws Exception {
		BankTransaction bankTransaction = basService.loadBankTransaction(Banks.SUNCORP,
				"src/test/resources/bas/TabDelimited20171020.asc");
		assertTrue(Paths.get(getPathStr("bas"), "banktransaction-SUNCORP.json").toFile().exists());
		assertNotNull(basService.related(Banks.SUNCORP, new Transaction(Utils.toDate("12/07/2017"),
				"INTERNET EXTERNAL TRANSFER TO 064475 010276526 REF NO 23920820 Deposit refund", -120.00, 3766.29)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("25/07/2017"), "DIRECT CREDIT   ATO ATO42253612817R207", 20.00, 2969.60)));
		assertNotNull(basService.unrelated(Banks.SUNCORP, new Transaction(Utils.toDate("25/07/2017"),
				"DIRECT CREDIT   ATO ATO42253612817R207", 240.00, 3209.60)));
		assertNotNull(basService.unrelated(Banks.SUNCORP, new Transaction(Utils.toDate("26/07/2017"),
				"DIRECT CREDIT   ATO ATO42253612817R208", 273.00, 3460.58)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("10/08/2017"), "DIRECT CREDIT   ATO ATO001000008928605", 2.52, 3094.32)));
		assertNotNull(basService.related(Banks.SUNCORP,
				new Transaction(Utils.toDate("12/09/2017"),
						"INTERNET EXTERNAL TRANSFER TO 014219 457147141 REF NO 69550912 Deposit Refund EFFECTIVE DATE 11/09/2017",
						-180.00, 3797.23)));
		assertNotNull(basService.related(Banks.SUNCORP, new Transaction(Utils.toDate("18/09/2017"),
				"INTERNET EXTERNAL TRANSFER TO 084034 413989363 REF NO 78905900 Deposit refund", -342.00, 4793.58)));
		BasQuarterly basQuarterly = basService.newBasQuarterly(BasQuarter.FY18Q1);

		assertEquals(basQuarterly.getIncome(), "11934.72");
		
		basService.addExpense(Expense.of(Retailers.ALDI, "01/07/2017",
				ImmutableList.of(Good.of("Baby Wipes FF 80pk", 1.89, 2),
						Good.of("BR: Minnie Telephone", 19.99),
						Good.of("Lic Wooden Puzzles", 8.99),
						Good.of("Toilet Cleaner Bleach", 2.19),
						Good.of("Ladies LW Gymshoe", 16.99))));
		basService.addExpense(Expense.of(Retailers.COSTCO, "01/07/2017",
				ImmutableList.of(Good.of("KS WATER", 5.89),
						Good.of("BR: Minnie Telephone", 19.99),
						Good.of("Lic Wooden Puzzles", 8.99),
						Good.of("Toilet Cleaner Bleach", 2.19),
						Good.of("Ladies LW Gymshoe", 16.99))));
	}

	@Test
	@Ignore
	public void loadBankTransaction() throws IOException {
		BankTransaction bankTransaction = basService.loadBankTransaction(Banks.SUNCORP,
				"src/test/resources/bas/TabDelimited20170630.asc");
		assertTrue(Paths.get(getPathStr("bas"), "banktransaction-SUNCORP.json").toFile().exists());
		bankTransaction = basService.loadBankTransaction(Banks.SUNCORP,
				"src/test/resources/bas/TabDelimited20171020.asc");
		assertEquals(bankTransaction.transactionCount(), 211);
		assertNotNull(basService.unrelated(Banks.SUNCORP, new Transaction(Utils.toDate("03/03/2017"),
				"DIRECT CREDIT   LYONESS COMMISSI Member Payment", 35.02, 1481.90)));
		assertDoubleEquals(bankTransaction.incomeFQ(BasQuarter.FY17Q3), 0.0);
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("21/04/2017"), "DIRECT CREDIT   WANJE JO Car", 352.00, 1679.58)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("22/05/2017"), "DIRECT CREDIT   MEENA LEE Repay", 50.00, 1501.43)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("23/05/2017"), "DIRECT CREDIT   TAE LEE shinae kim", 95.00, 1596.43)));
		assertNotNull(basService.related(Banks.SUNCORP, new Transaction(Utils.toDate("15/06/2017"),
				"INTERNET EXTERNAL TRANSFER TO 064475 010276526 REF NO 10993890 AspleyFamilyDC", -60.00, 2182.02)));
		assertDoubleEquals(bankTransaction.incomeFQ(BasQuarter.FY17Q4), 6332.97);

		// 2018Q1
		assertNotNull(basService.related(Banks.SUNCORP, new Transaction(Utils.toDate("12/07/2017"),
				"INTERNET EXTERNAL TRANSFER TO 064475 010276526 REF NO 23920820 Deposit refund", -120.00, 3766.29)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("25/07/2017"), "DIRECT CREDIT   ATO ATO42253612817R207", 20.00, 2969.60)));
		assertNotNull(basService.unrelated(Banks.SUNCORP, new Transaction(Utils.toDate("25/07/2017"),
				"DIRECT CREDIT   ATO ATO42253612817R207", 240.00, 3209.60)));
		assertNotNull(basService.unrelated(Banks.SUNCORP, new Transaction(Utils.toDate("26/07/2017"),
				"DIRECT CREDIT   ATO ATO42253612817R208", 273.00, 3460.58)));
		assertNotNull(basService.unrelated(Banks.SUNCORP,
				new Transaction(Utils.toDate("10/08/2017"), "DIRECT CREDIT   ATO ATO001000008928605", 2.52, 3094.32)));
		assertNotNull(basService.related(Banks.SUNCORP,
				new Transaction(Utils.toDate("12/09/2017"),
						"INTERNET EXTERNAL TRANSFER TO 014219 457147141 REF NO 69550912 Deposit Refund EFFECTIVE DATE 11/09/2017",
						-180.00, 3797.23)));
		assertNotNull(basService.related(Banks.SUNCORP, new Transaction(Utils.toDate("18/09/2017"),
				"INTERNET EXTERNAL TRANSFER TO 084034 413989363 REF NO 78905900 Deposit refund", -342.00, 4793.58)));

		assertDoubleEquals(bankTransaction.incomeFQ(BasQuarter.FY18Q1), 11934.72);
		assertDoubleEquals(bankTransaction.incomeFQ(BasQuarter.FY18Q2), 2641.15);
	}
}
