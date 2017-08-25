package com.ktiteng.entity.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.ktiteng.entity.BaseEntity;
import com.ktiteng.util.Utils;

public class TimeCard extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String childId;
	private List<LocalDate> dates;
	private LocalTime startTime;
	private LocalTime endTime;
	private List<TimeCardEntry> timeCard;

	public TimeCard() {
		setId(Utils.getId());
	}

	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}

	public List<LocalDate> getDates() {
		return dates;
	}

	public void setDates(List<LocalDate> dates) {
		this.dates = dates;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public List<TimeCardEntry> getTimeCard() {
		return timeCard;
	}

	public void setTimeCard(List<TimeCardEntry> timeCard) {
		this.timeCard = timeCard;
	}

}
