package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 201404132212L;

    private String id = IDGenerator.createId();
    private String street;
    private String number;
    private String country;
    private String zip;
    private String city;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("street", street)
            .append("number", number)
            .append("country", country)
            .append("zip", zip)
            .append("city", city)
            .toString();
    }

    /**
     * Indicates wether the address has any values set.
     *
     * @return true if any value has been set, false otherwise
     */
    public boolean hasValues() {
        if(!StringUtils.isBlank(street)) {
            return true;
        }
        if(!StringUtils.isBlank(number)) {
            return true;
        }
        if(!StringUtils.isBlank(country)) {
            return true;
        }
        if(!StringUtils.isBlank(zip)) {
            return true;
        }
        if(!StringUtils.isBlank(city)) {
            return true;
        }

        return false;
    }
}
