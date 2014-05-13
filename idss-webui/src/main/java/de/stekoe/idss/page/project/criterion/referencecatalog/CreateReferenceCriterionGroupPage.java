package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.CriterionGroupService;

public class CreateReferenceCriterionGroupPage extends ReferenceCriterionPage {

    @Inject
    CriterionGroupService criterionGroupService;

    public CreateReferenceCriterionGroupPage() {
        add(new CreateCriterionGroupForm("form", new Model(new CriterionGroup())) {
            @Override
            public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {
                CriterionGroup object = iModel.getObject();
                object.setReferenceType(true);
                criterionGroupService.save(object);
            }
        });
    }
}
