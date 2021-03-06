package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.service.ReferenceCriterionGroupService;
import de.stekoe.idss.session.WebSession;

public class EditReferenceCriterionGroupPage extends ReferenceCriterionPage {

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    public EditReferenceCriterionGroupPage(PageParameters parameters) {
        super(parameters);

        String criterionGroupId = parameters.get("criterionGroupId").toString(null);
        if(criterionGroupId == null) {
            WebSession.get().error("Fail");
            setResponsePage(ReferenceCriterionGroupListPage.class);
            return;
        }

        add(new CriterionGroupForm("form", criterionGroupId) {
            @Override
            public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {
                CriterionGroup object = iModel.getObject();
                referenceCriterionGroupService.save(object);

                WebSession.get().success(getString("message.save.success"));
                setResponsePage(ReferenceCriterionGroupListPage.class);
            }
        });
    }
}
