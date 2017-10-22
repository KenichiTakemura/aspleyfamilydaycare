package com.ktiteng.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

public class Utils {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/y");
	private static NumberFormat numberFormatter = new DecimalFormat("#0.00");
	private static DecimalFormat df = new DecimalFormat("#.##");
	private Utils() {
	}

	public static String getId() {
		return UUID.randomUUID().toString();
	}

	public static LocalDate toDate(String date) {
		if (date.contains("-")) {
			return LocalDate.parse(date);
		} else {
			try {
				return LocalDate.parse(date, dateFormatter);
			} catch (DateTimeParseException e) {
				return LocalDate.parse(date, DateTimeFormatter.ofPattern("y/M/d"));
			}
		}
	}
	
	public static String df(double number) {
		return df.format(number);
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
	
	public static boolean isPositive(double d) {
		return Double.compare(d, 0.0) > 0;
	}

}
