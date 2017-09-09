package com.ktiteng.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Utils {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static NumberFormat numberFormatter = new DecimalFormat("#0.00");

	private Utils() {
	}

	public static String getId() {
		return UUID.randomUUID().toString();
	}

	public static LocalDate toDate(String date) {
		return LocalDate.parse(date);
	}

	public static String toS(LocalDate date) {
		return dateFormatter.format(date);
	}

	public static String toS(double val) {
		return numberFormatter.format(val);
	}

	public static boolean isNullOrBlank(String param) {
		return param == null || param.trim().length() == 0;
	}

}
