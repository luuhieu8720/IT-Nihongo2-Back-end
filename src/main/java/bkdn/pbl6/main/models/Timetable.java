package bkdn.pbl6.main.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import bkdn.pbl6.main.enums.Day;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Timetable {

	private Day day;

	private Integer startHour;

	private Integer startMinus;

	private Integer endHour;

	private Integer endMinus;

	public Timetable() {
	}

	public Timetable(Day day, Integer startHour, Integer startMinus, Integer endHour, Integer endMinus) {
		this.day = day;
		this.startHour = startHour;
		this.startMinus = startMinus;
		this.endHour = endHour;
		this.endMinus = endMinus;
	}

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Integer getStartHour() {
		return startHour;
	}

	public void setStartHour(Integer startHour) {
		this.startHour = startHour;
	}

	public Integer getStartMinus() {
		return startMinus;
	}

	public void setStartMinus(Integer startMinus) {
		this.startMinus = startMinus;
	}

	public Integer getEndHour() {
		return endHour;
	}

	public void setEndHour(Integer endHour) {
		this.endHour = endHour;
	}

	public Integer getEndMinus() {
		return endMinus;
	}

	public void setEndMinus(Integer endMinus) {
		this.endMinus = endMinus;
	}

}
