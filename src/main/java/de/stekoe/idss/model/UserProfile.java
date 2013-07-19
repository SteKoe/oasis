package de.stekoe.idss.model;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;

public class UserProfile {
	private Long id;
	private String firstname;
	private String surename;
	private Date birthdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurename() {
		return surename;
	}

	public void setSurename(String surename) {
		this.surename = surename;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public int getAge() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getBirthdate());

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		DateMidnight birthdate = new DateMidnight(year, month, day);
		DateTime currentDate = getCurrentDate();

		return Years.yearsBetween(birthdate, currentDate).getYears();
	}

	// For testing purposes
	DateTime getCurrentDate() {
		return new DateTime();
	}
}
