package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;
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

    private String id;
    private String firstname;
    private String surname;
    private Date birthdate;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
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
