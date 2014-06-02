package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 201404132235L;

    private String id = IDGenerator.createId();
    private String countryCode;
    private String areaCode;
    private String subscriberNumber;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    @Transient
    public boolean hasNumber() {
        if(StringUtils.isBlank(getCountryCode()) && StringUtils.isBlank(getAreaCode()) && StringUtils.isBlank(getSubscriberNumber())) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof PhoneNumber)) return false;

        PhoneNumber that  = (PhoneNumber) other;
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
