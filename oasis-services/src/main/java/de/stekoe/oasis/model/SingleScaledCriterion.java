package de.stekoe.oasis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;

@Entity
public abstract class SingleScaledCriterion<T extends MeasurementValue> extends Criterion implements IValueBean {
    private static final long serialVersionUID = 20141207;

    public SingleScaledCriterion() {
        // NOP
    }

    public SingleScaledCriterion(SingleScaledCriterion criterion) {
        super(criterion);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof SingleScaledCriterion)) return false;

        SingleScaledCriterion that = (SingleScaledCriterion) other;
        return new EqualsBuilder()
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