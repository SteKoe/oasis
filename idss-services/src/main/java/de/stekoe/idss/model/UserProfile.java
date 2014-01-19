package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "UserProfile")
public class UserProfile implements Serializable {

    private java.lang.String id = IDGenerator.createId();
    private java.lang.String firstname;
    private java.lang.String surname;
    private Date birthdate;
    private User user;

    @Id
    @Column(name = "user_profile_id")
    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @Column
    public java.lang.String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(java.lang.String firstname) {
        this.firstname = firstname;
    }

    @Column
    public java.lang.String getSurname() {
        return this.surname;
    }

    public void setSurname(java.lang.String surname) {
        this.surname = surname;
    }

    @Past
    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @OneToOne(mappedBy = "profile")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    public int getAge() {
        DateMidnight birthdate = new DateMidnight(getBirthdate());
        return Years.yearsBetween(birthdate, getCurrentDate()).getYears();
    }

    @Transient
    // Package private for testing purpose
    DateTime getCurrentDate() {
        return new DateTime();
    }

}
