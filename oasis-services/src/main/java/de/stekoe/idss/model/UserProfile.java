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
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class UserProfile implements Serializable {
    private static final long serialVersionUID = 201404031316L;

    private String id = IDGenerator.createId();
    private String nameSuffix;
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

    public String getNameSuffix() {
        return nameSuffix;
    }
    public void setNameSuffix(String nameSuffix) {
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

    @Transient
    public String getFullName() {
        StringBuilder sb = new StringBuilder();

        boolean hasFirstname = !StringUtils.isBlank(getFirstname());
        boolean hasSurname = !StringUtils.isBlank(getSurname());

        if(hasFirstname) {
            sb.append(getFirstname());
        }
        if(hasFirstname && hasSurname) {
            sb.append(" ");
        }
        if(hasSurname) {
            sb.append(getSurname());
        }
        String fullname = sb.toString();
        if(StringUtils.isBlank(fullname)) {
            return null;
        } else {
            return fullname;
        }
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof UserProfile)) return false;

        UserProfile that  = (UserProfile) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

}
