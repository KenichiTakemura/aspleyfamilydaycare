package com.ktiteng.entity;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeCardEntry extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDate date;
	private LocalTime startTimePlanned;
	private LocalTime endTimePlanned;
	private LocalTime startTimeActual;
	private LocalTime endTimeActual;
	private boolean absent;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTimePlanned() {
		return startTimePlanned;
	}

	public void setStartTimePlanned(LocalTime startTimePlanned) {
		this.startTimePlanned = startTimePlanned;
	}

	public LocalTime getEndTimePlanned() {
		return endTimePlanned;
	}

	public void setEndTimePlanned(LocalTime endTimePlanned) {
		this.endTimePlanned = endTimePlanned;
	}

	public LocalTime getStartTimeActual() {
		return startTimeActual;
	}

	public void setStartTimeActual(LocalTime startTimeActual) {
		this.startTimeActual = startTimeActual;
	}

	public LocalTime getEndTimeActual() {
		return endTimeActual;
	}

	public void setEndTimeActual(LocalTime endTimeActual) {
		this.endTimeActual = endTimeActual;
	}

	public boolean isAbsent() {
		return absent;
	}

	public void setAbsent(boolean absent) {
		this.absent = absent;
	}

}
