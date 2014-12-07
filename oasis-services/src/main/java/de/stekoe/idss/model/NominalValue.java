package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;

@Entity
public class NominalValue extends MeasurementValue {
    private static final long serialVersionUID = 20141207;

    private NominalScaledCriterion criterion;

    public NominalValue() {
        // NOP
    }

    public NominalValue(NominalValue nominalValue) {
        super(nominalValue);
    }

    public NominalValue(String value) {
        super(value);
    }

    @ManyToOne(targetEntity = NominalScaledCriterion.class)
    @OrderColumn(name = "ordering")
    public NominalScaledCriterion getCriterion() {
        return criterion;
    }
    public void setCriterion(NominalScaledCriterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof NominalValue)) return false;

        NominalValue that = (NominalValue) other;
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
