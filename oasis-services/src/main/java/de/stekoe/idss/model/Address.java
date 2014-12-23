package de.stekoe.idss.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
public class Address implements Serializable {
    private static final long serialVersionUID = 201404132212L;

    private String id;
    private String street;
    private String number;
    private String country;
    private String zip;
    private String city;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 5)
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    @NotNull
    @NotEmpty
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    @NotNull
    @Size(min = 5)
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull
    @Size(min = 1)
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    @NotNull
    @Size(min = 1)
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Indicates wether the address has any values set.
     *
     * @return true if any value has been set, false otherwise
     */
    @Transient
    public boolean isEmpty() {
        if (!StringUtils.isBlank(street)) {
            return false;
        }
        if (!StringUtils.isBlank(number)) {
            return false;
        }
        if (!StringUtils.isBlank(country)) {
            return false;
        }
        if (!StringUtils.isBlank(zip)) {
            return false;
        }
        if (!StringUtils.isBlank(city)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Address)) return false;

        Address that = (Address) other;
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
