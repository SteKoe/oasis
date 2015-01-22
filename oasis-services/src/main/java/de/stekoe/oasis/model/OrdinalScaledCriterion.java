package de.stekoe.oasis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OrdinalScaledCriterion extends SingleScaledCriterion<OrdinalValue> {
    private static final long serialVersionUID = 20141207;

    private List<OrdinalValue> values = new ArrayList<>();

    public OrdinalScaledCriterion() {
    }

    public OrdinalScaledCriterion(OrdinalScaledCriterion ordinalScaledCriterion) {
        super(ordinalScaledCriterion);
        for(OrdinalValue ov : ordinalScaledCriterion.getValues()) {
            OrdinalValue ordinalValue = new OrdinalValue(ov);
            ordinalValue.setCriterion(this);
            getValues().add(ordinalValue);
        }
    }

    @OneToMany(mappedBy = "criterion", targetEntity = OrdinalValue.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "ordering")
    public List<OrdinalValue> getValues() {
        return this.values;
    }
    public void setValues(List<OrdinalValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof OrdinalScaledCriterion)) return false;

        OrdinalScaledCriterion that = (OrdinalScaledCriterion) other;
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
