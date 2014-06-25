package de.stekoe.idss.page.project.criterion.referencecatalog;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.CreateOrdinalScaledCriterionForm;
import de.stekoe.idss.service.CriterionService;

public class CreateOrdinalReferenceCriterionPage extends ReferenceCriterionPage {

    @Inject
    private CriterionService criterionService;

    public CreateOrdinalReferenceCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        CreateOrdinalScaledCriterionForm createForm = new CreateOrdinalScaledCriterionForm("form", null) {
            @Override
            public void onSaveCriterion(IModel<SingleScaledCriterion<OrdinalValue>> aModel) {
                final SingleScaledCriterion<OrdinalValue> criterion = aModel.getObject();
                if(StringUtils.isEmpty(criterion.getName())) {
                    criterion.setName(getString("label.criterion.type.Ordinal"));
                }
                criterion.setReferenceType(true);
                criterionService.save(criterion);

                getWebSession().success(getString("message.save.success"));

                final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
                setResponsePage(ReferenceCriterionListPage.class);
            }
        };
        add(createForm);
    }
}
