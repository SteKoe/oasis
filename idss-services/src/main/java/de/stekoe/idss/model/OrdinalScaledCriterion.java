package de.stekoe.idss.model;

import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class OrdinalScaledCriterion extends SingleScaledCriterion<OrdinalValue> {
    private static final long serialVersionUID = 201404132234L;

    public OrdinalScaledCriterion() {
    }

    public OrdinalScaledCriterion(OrdinalScaledCriterion ordinalScaledCriterion) {
        super(ordinalScaledCriterion);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append(super.toString())
            .toString();
    }
}
