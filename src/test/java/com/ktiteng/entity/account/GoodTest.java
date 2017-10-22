package com.ktiteng.entity.account;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class GoodTest {

	@Test
	public void of() throws IOException {
		Path file = Paths.get(System.getProperty("user.home"), ".afdc", "master", "bas", "2017Q3", "expense.txt");
		try (BufferedReader br = new BufferedReader(new FileReader(file.toString()))) {
			String line;
			String currentDate = null;
			Retailers currentRetailers = null;
			Expense expense = null;
			BasQuarterly basQuarterly = new BasQuarterly(BasQuarter.FY17Q3);
			BasQuarterlyDetails basQuarterlyDetails = new BasQuarterlyDetails(basQuarterly.id());
		    while ((line = br.readLine()) != null) {
		    	String[] row = line.split("\t");
		    	String date = row[0];
		    	String name = row[1];
		    	Retailers retailers = Retailers.valueOf(row[2]);
		    	double unitPrice = Double.parseDouble(row[3]);
		    	int quantity = Integer.parseInt(row[6]);
		    	boolean requiresNew = false;
		    	if (currentDate == null || !currentDate.equals(date)) {
		    		currentDate = date;
		    		requiresNew = true;
		    	}
		    	if (currentRetailers == null || currentRetailers != retailers) {
		    		currentRetailers = retailers;
		    		requiresNew = true;
		    	}
		    	if (requiresNew) {
		    		if (expense != null) {
		    			basQuarterlyDetails.addExpenses(expense);
		    		}
		    		expense = Expense.of(currentRetailers, currentDate);
		    	}
		    	expense.addGoods(Good.of(name, unitPrice, quantity));
		    }
		    basQuarterlyDetails.addExpenses(expense);
		    System.out.println("Expense " + basQuarterlyDetails.getExpense().doubleValue());
		    System.out.println("GST " + basQuarterlyDetails.getGstClaimable().doubleValue());
		    System.out.println("Dedection " + basQuarterlyDetails.getDecuction().doubleValue());
		}
	}
}
