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

    private static final long serialVersionUID = 101403011956L;

    private String id = IDGenerator.createId();
    private String firstname;
    private String surname;
    private Date birthdate;
    private User user;

    @Id
    @Column(name = "user_profile_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column
    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
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
        DateMidnight birthdateMidnight = new DateMidnight(getBirthdate());
        return Years.yearsBetween(birthdateMidnight, getCurrentDate()).getYears();
    }

    @Transient
        // Package private for testing purpose
    DateTime getCurrentDate() {
        return new DateTime();
    }

}
