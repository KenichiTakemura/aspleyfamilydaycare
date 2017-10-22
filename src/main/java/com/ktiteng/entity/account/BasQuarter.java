package com.ktiteng.entity.account;

import java.time.LocalDate;

public enum BasQuarter {

	FY17Q3("2017-01-01"), FY17Q4("2017-04-01"), FY18Q1("2017-07-01"), FY18Q2("2017-10-01"),
	FY18Q3("2018-01-01"), FY18Q4("2018-04-01");

	LocalDate startDate;
	LocalDate endDate;

	BasQuarter(String startDate) {
		this.startDate = LocalDate.parse(startDate);
		this.endDate = this.startDate.plusMonths(3).minusDays(1);
	}
	
	public boolean isWithin(LocalDate date) {
		if (date.equals(startDate) || date.equals(endDate) ||
				date.isAfter(startDate) && date.isBefore(endDate)) {
			return true;
		}
		return false;
	}
	
	public static BasQuarter findByDate(LocalDate date) {
		for(BasQuarter bq: BasQuarter.values()) {
			if (bq.isWithin(date)) {
				return bq;
			}
		}
		return null;
	}
}
