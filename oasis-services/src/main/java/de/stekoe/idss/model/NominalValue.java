package de.stekoe.idss.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class NominalValue extends MeasurementValue {
    private static final long serialVersionUID = 201404132234L;

    public NominalValue() {
    }

    public NominalValue(NominalValue nominalValue) {
        super(nominalValue);
    }

    public NominalValue(String value) {
        super(value);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof NominalValue)) return false;

        NominalValue that  = (NominalValue) other;
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
