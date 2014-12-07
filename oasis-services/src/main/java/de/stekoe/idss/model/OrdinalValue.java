package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;

@Entity
public class OrdinalValue extends MeasurementValue {
    private static final long serialVersionUID = 20141207;

    private OrdinalScaledCriterion criterion;
    private Double weight = 1.0;

    public OrdinalValue() {
        // NOP
    }

    public OrdinalValue(OrdinalValue ordinalValue) {
        super(ordinalValue);
    }

    public OrdinalValue(String value) {
        super(value);
    }

    @ManyToOne(targetEntity = OrdinalScaledCriterion.class)
    @OrderColumn(name = "ordering")
    public OrdinalScaledCriterion getCriterion() {
        return criterion;
    }
    public void setCriterion(OrdinalScaledCriterion criterion) {
        this.criterion = criterion;
    }

    @Column(nullable = false)
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OrdinalValue)) return false;

        OrdinalValue that = (OrdinalValue) other;
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
