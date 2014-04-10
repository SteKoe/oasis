/*
 * Copyright 2014 Stephan Koeninger Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class UserProfile implements Serializable {
    private static long serialVersionUID = 201404031316L;

    private String id = IDGenerator.createId();
    private NameSuffix nameSuffix;
    private String firstname;
    private String surname;
    private PhoneNumber telefon = new PhoneNumber();
    private PhoneNumber telefax = new PhoneNumber();
    private Address address = new Address();
    private String website;

    @Id
    public String getId() {
        return id;
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

    @Transient
    public String getFullName() {
        String name = getFirstname() + " " + getSurname();
        return name;
    }

    @OneToOne(targetEntity = PhoneNumber.class, cascade = CascadeType.ALL)
    public PhoneNumber getTelefon() {
        return telefon;
    }

    public void setTelefon(PhoneNumber telefon) {
        this.telefon = telefon;
    }

    @OneToOne(targetEntity = PhoneNumber.class, cascade = CascadeType.ALL)
    public PhoneNumber getTelefax() {
        return telefax;
    }

    public void setTelefax(PhoneNumber telefax) {
        this.telefax = telefax;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public static void setSerialversionuid(long serialversionuid) {
        serialVersionUID = serialversionuid;
    }

    @Enumerated(value = EnumType.STRING)
    public NameSuffix getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(NameSuffix nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
