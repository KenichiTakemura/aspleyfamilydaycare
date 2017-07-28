package com.ktiteng.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TimeCard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<LocalDate> dates;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<TimeCardEntry> timeCard;
	
}
