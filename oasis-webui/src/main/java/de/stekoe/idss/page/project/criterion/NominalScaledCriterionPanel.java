package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.NominalScaledCriterion;

public abstract class NominalScaledCriterionPanel extends SingleScaledCriterionPanel<NominalScaledCriterion> {
    public NominalScaledCriterionPanel(String wicketId, NominalScaledCriterion criterion) {
        super(wicketId, criterion);
        renderChoices();
    }

    abstract void renderChoices();
}
