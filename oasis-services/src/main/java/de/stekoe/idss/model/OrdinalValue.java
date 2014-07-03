package de.stekoe.idss.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class OrdinalValue extends MeasurementValue {
    private static final long serialVersionUID = 201404132235L;

    public OrdinalValue() {
    }

    public OrdinalValue(OrdinalValue ordinalValue) {
        super(ordinalValue);
    }

    public OrdinalValue(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof OrdinalValue)) return false;

        OrdinalValue that  = (OrdinalValue) other;
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
