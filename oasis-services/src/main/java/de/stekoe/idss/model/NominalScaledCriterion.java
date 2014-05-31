package de.stekoe.idss.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public class NominalScaledCriterion extends SingleScaledCriterion<NominalValue> {
    private static final long serialVersionUID = 201404132234L;

    private boolean multipleChoice = false;

    public NominalScaledCriterion() {
    }

    public NominalScaledCriterion(NominalScaledCriterion nominalScaledCriterion) {
        super(nominalScaledCriterion);

        multipleChoice = nominalScaledCriterion.isMultipleChoice();
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isMultipleChoice() {
        return multipleChoice;
    }
    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}