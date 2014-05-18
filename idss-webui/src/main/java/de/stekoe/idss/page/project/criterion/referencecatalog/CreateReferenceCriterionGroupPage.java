package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.ReferenceCriterionGroupService;
import de.stekoe.idss.session.WebSession;

public class CreateReferenceCriterionGroupPage extends ReferenceCriterionPage {

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    public CreateReferenceCriterionGroupPage() {
        add(new CriterionGroupForm("form", null) {
            @Override
            public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {
                CriterionGroup referenceCriterionGroup = iModel.getObject();
                referenceCriterionGroup.setReferenceType(true);
                criterionGroupService.save(referenceCriterionGroup);

                WebSession.get().success(getString("message.save.success"));
                setResponsePage(ReferenceCriterionGroupListPage.class);
            }
        });
    }
}
