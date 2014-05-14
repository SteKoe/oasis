package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.session.WebSession;

public class CreateReferenceCriterionGroupPage extends ReferenceCriterionPage {

    @Inject
    CriterionGroupService criterionGroupService;

    public CreateReferenceCriterionGroupPage() {
        add(new CriterionGroupForm("form", null) {
            @Override
            public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {
                CriterionGroup object = iModel.getObject();
                object.setReferenceType(true);
                CriterionGroup save = criterionGroupService.save(object);

                WebSession.get().success(getString("message.save.success"));
                setResponsePage(ReferenceCriterionGroupListPage.class);
            }
        });
    }
}
