package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.OrdinalScaledCriterion;

public abstract class OrdinalScaledCriterionPanel extends SingleScaledCriterionPanel<OrdinalScaledCriterion> {

    public OrdinalScaledCriterionPanel(String wicketId, OrdinalScaledCriterion criterion) {
        super(wicketId, criterion);
        renderChoices();
    }

    abstract void renderChoices();
}
