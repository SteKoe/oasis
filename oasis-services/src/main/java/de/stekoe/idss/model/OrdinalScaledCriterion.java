package de.stekoe.idss.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class OrdinalScaledCriterion extends SingleScaledCriterion<OrdinalValue> {
    private static final long serialVersionUID = 201404132234L;

    public OrdinalScaledCriterion() {
    }

    public OrdinalScaledCriterion(OrdinalScaledCriterion ordinalScaledCriterion) {
        super(ordinalScaledCriterion);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof OrdinalScaledCriterion)) return false;

        OrdinalScaledCriterion that  = (OrdinalScaledCriterion) other;
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
