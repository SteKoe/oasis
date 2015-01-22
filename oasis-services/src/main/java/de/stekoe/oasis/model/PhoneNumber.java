package de.stekoe.oasis.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class PhoneNumber implements Serializable {
    private static final long serialVersionUID = 201404132235L;

    private String id;
    private String countryCode;
    private String areaCode;
    private String subscriberNumber;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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
    public boolean isEmpty() {
        if (StringUtils.isBlank(getCountryCode()) && StringUtils.isBlank(getAreaCode()) && StringUtils.isBlank(getSubscriberNumber())) {
            return true;
        } else {
            return false;
        }
    }

    @Transient
    public String asString() {
        StringBuilder sb = new StringBuilder();

        if(getCountryCode().startsWith("+")) {
            sb.append(getCountryCode());
        } else if(getCountryCode().startsWith("00")) {
            sb.append("+").append(getCountryCode().substring(2));
        }

        if(!getCountryCode().isEmpty()) {
            sb.append(" ");
            sb.append("(" + getAreaCode().replaceFirst("0","") + ") ");
        } else {
            sb.append(getAreaCode());
        }
        sb.append(" ");
        sb.append(getSubscriberNumber());

        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof PhoneNumber)) return false;

        PhoneNumber that = (PhoneNumber) other;
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
