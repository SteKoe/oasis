package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.CriterionGroup;

public class CreateCriterionGroupForm extends CriterionGroupForm {

    @Inject

    public CreateCriterionGroupForm(String wicketId, IModel<?> model) {
        super(wicketId, model);
    }

    @Override
    public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {

    }
}
