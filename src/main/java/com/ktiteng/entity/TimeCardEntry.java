package com.ktiteng.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeCardEntry extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	LocalDate date;
	LocalTime startTimePlanned;
	LocalTime endTimePlanned;
	LocalTime startTimeActual;
	LocalTime endTimeActual;
	boolean absent;
}
