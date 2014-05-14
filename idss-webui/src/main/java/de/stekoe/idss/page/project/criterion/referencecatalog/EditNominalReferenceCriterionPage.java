package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditNominalCriterionForm;
import de.stekoe.idss.service.CriterionService;

public class EditNominalReferenceCriterionPage extends ReferenceCriterionPage {

    @Inject
    private CriterionService criterionService;

    public EditNominalReferenceCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        EditNominalCriterionForm editForm = new EditNominalCriterionForm("form", aPageParams.get("criterionId").toString()) {
            @Override
            public void onSaveCriterion(IModel<SingleScaledCriterion<NominalValue>> aModel) {
                SingleScaledCriterion<NominalValue> criterion = aModel.getObject();
                criterionService.saveCriterion(criterion);

                getWebSession().success(getString("message.save.success"));

                final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
                setResponsePage(EditNominalReferenceCriterionPage.class, pageParams);
            }
        };

        add(editForm);
    }
}
