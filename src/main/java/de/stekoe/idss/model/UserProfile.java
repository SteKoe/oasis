package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;

/**
 * This class holds any information about a user's profile.
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserProfile implements Serializable {
    private Long id;
    private String firstname;
    private String surename;
    private Date birthdate;

    /**
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id Id of the UserProfile.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname The first name of the user.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the surename.
     */
    public String getSurename() {
        return surename;
    }

    /**
     * Sets the surename.
     *
     * @param surename The surename of the user.
     */
    public void setSurename(String surename) {
        this.surename = surename;
    }

    /**
     * @return the birthdate.
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Sets the birthdate.
     *
     * @param birthdate The birthdate of the user.
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return the age of a user.
     */
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

    /**
     * @return the current date.
     */
    // For testing purposes
    DateTime getCurrentDate() {
        return new DateTime();
    }
}
