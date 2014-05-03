package de.stekoe.idss.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NominalScaledCriterion extends SingleScaledCriterion<NominalValue> {
    private static final long serialVersionUID = 201404132234L;

    private boolean multipleChoice = false;

    @Column(columnDefinition = "boolean default false")
    public boolean isMultipleChoice() {
        return multipleChoice;
    }
    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }
}