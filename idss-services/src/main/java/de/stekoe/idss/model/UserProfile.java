/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    private UserProfileId id = new UserProfileId();
    private String firstname;
    private String surname;
    private Date birthdate;

    @EmbeddedId
    public UserProfileId getId() {
        return this.id;
    }

    public void setId(UserProfileId id) {
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
        DateMidnight birthdateMidnight = new DateMidnight(getBirthdate());
        return Years.yearsBetween(birthdateMidnight, getCurrentDate()).getYears();
    }

    // Package private for testing purpose
    @Transient
    DateTime getCurrentDate() {
        return new DateTime();
    }

}
