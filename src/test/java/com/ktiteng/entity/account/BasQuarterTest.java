package com.ktiteng.entity.account;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class BasQuarterTest {

	@Test
	public void getQuaterEndDate() {
		assertEquals(BasQuarter.FY17Q3.endDate.toString(), "2017-03-31");
		assertEquals(BasQuarter.FY17Q4.endDate.toString(), "2017-06-30");
		assertEquals(BasQuarter.FY18Q1.endDate.toString(), "2017-09-30");
		assertEquals(BasQuarter.FY18Q2.endDate.toString(), "2017-12-31");
		assertEquals(BasQuarter.FY18Q3.endDate.toString(), "2018-03-31");
		assertEquals(BasQuarter.FY18Q4.endDate.toString(), "2018-06-30");
	}
	
	@Test
	public void findByDate() {
		assertEquals(BasQuarter.FY17Q4, BasQuarter.findByDate(LocalDate.parse("2017-06-30")));
		assertEquals(BasQuarter.FY18Q1, BasQuarter.findByDate(LocalDate.parse("2017-07-01")));
		assertEquals(BasQuarter.FY18Q1, BasQuarter.findByDate(LocalDate.parse("2017-09-30")));
		assertEquals(BasQuarter.FY18Q2, BasQuarter.findByDate(LocalDate.parse("2017-10-01")));
		assertEquals(BasQuarter.FY18Q2, BasQuarter.findByDate(LocalDate.parse("2017-10-02")));
	}
}
