package de.stekoe.idss.page.project.criterion.page;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.page.project.criterion.referencecatalog.CriterionGroupForm;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.session.WebSession;

public class EditCriterionGroupPage extends ProjectPage {

    @Inject
    CriterionGroupService criterionGroupService;

    public EditCriterionGroupPage(PageParameters parameters) {
        super(parameters);

        String criterionGroupId = parameters.get("criterionGroupId").toString(null);
        if(criterionGroupId == null) {
            WebSession.get().error("Fail");
            setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters());
            return;
        }

        add(new CriterionGroupForm("form", criterionGroupId) {

            @Override
            protected void onInitialize() {
                super.onInitialize();

                disableCriterionSelectionList();
            }

            @Override
            public void onSaveCriterionGroup(IModel<CriterionGroup> iModel) {
                CriterionGroup criterionGroup = iModel.getObject();
                criterionGroupService.save(criterionGroup);

                WebSession.get().success(getString("message.save.success"));

                final PageParameters pageParams = new PageParameters().add("criterionGroupId", criterionGroup.getId());
                setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
            }
        });
    }
}
