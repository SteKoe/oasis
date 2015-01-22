package de.stekoe.oasis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NominalScaledCriterion extends SingleScaledCriterion<NominalValue> {
    private static final long serialVersionUID = 20141207;

    private boolean multipleChoice = false;
    private List<NominalValue> values = new ArrayList<>();

    public NominalScaledCriterion() {
    }

    public NominalScaledCriterion(NominalScaledCriterion nominalScaledCriterion) {
        super(nominalScaledCriterion);
        multipleChoice = nominalScaledCriterion.isMultipleChoice();

        for(NominalValue nv : nominalScaledCriterion.getValues()) {
            NominalValue nominalValue = new NominalValue(nv);
            nominalValue.setCriterion(this);
            getValues().add(nominalValue);
        }
    }

    @Column
    public boolean isMultipleChoice() {
        return multipleChoice;
    }
    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }


    @OneToMany(mappedBy = "criterion", targetEntity = NominalValue.class, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderColumn(name = "ordering")
    public List<NominalValue> getValues() {
        return this.values;
    }
    public void setValues(List<NominalValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof NominalScaledCriterion)) return false;

        NominalScaledCriterion that = (NominalScaledCriterion) other;
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