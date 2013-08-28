package de.stekoe.idss.model;

// Generated 26.08.2013 06:13:04 by Hibernate Tools 4.0.0

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class UserProfile implements java.io.Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Basic
    private String firstname;

    @Basic
    private String surename;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    private Date birthdate;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurename() {
        return this.surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        DateMidnight birthdate = new DateMidnight(getBirthdate());
        return Years.yearsBetween(birthdate, getCurrentDate()).getYears();
    }

    DateTime getCurrentDate() {
        return new DateTime();
    }

}
