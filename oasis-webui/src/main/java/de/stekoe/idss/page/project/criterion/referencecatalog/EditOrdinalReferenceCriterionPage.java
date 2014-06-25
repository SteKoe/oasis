package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionForm;
import de.stekoe.idss.service.CriterionService;

public class EditOrdinalReferenceCriterionPage extends ReferenceCriterionPage {

    @Inject
    private CriterionService criterionService;

    public EditOrdinalReferenceCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        EditOrdinalCriterionForm editForm = new EditOrdinalCriterionForm("form", aPageParams.get("criterionId").toString()) {
            @Override
            public void onSaveCriterion(IModel<SingleScaledCriterion<OrdinalValue>> aModel) {
                SingleScaledCriterion<OrdinalValue> criterion = aModel.getObject();
                criterionService.save(criterion);

                getWebSession().success(getString("message.save.success"));

                final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
                setResponsePage(EditOrdinalReferenceCriterionPage.class, pageParams);
            }
        };

        add(editForm);
    }
}
