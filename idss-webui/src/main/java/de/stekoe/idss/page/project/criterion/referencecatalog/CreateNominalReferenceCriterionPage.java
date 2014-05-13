package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.CreateNominalScaledCriterionForm;
import de.stekoe.idss.service.CriterionService;

public class CreateNominalReferenceCriterionPage extends ReferenceCriterionPage {

    @Inject
    private CriterionService criterionService;

    public CreateNominalReferenceCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        CreateNominalScaledCriterionForm createForm = new CreateNominalScaledCriterionForm("form", null) {
            @Override
            public void onSaveCriterion(IModel<SingleScaledCriterion<NominalValue>> aModel) {
                final SingleScaledCriterion<NominalValue> criterion = aModel.getObject();
                if(StringUtils.isEmpty(criterion.getName())) {
                    criterion.setName(getString("label.criterion.type.nominal"));
                }
                criterion.setReferenceType(true);
                criterionService.saveCriterion(criterion);

                getWebSession().success("Success");

                final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
                setResponsePage(ReferenceCriterionListPage.class);
            }
        };
        add(createForm);
    }
}
