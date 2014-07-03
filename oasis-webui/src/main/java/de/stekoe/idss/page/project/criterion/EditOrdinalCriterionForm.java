package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.session.WebSession;

public class EditOrdinalCriterionForm extends OrdinalScaledCriterionForm {

    @SpringBean
    private CriterionService itsCriterionService;

    public EditOrdinalCriterionForm(String aId, String aCriterionId) {
        super(aId, aCriterionId);
    }

    @Override
    public void onSaveCriterion(IModel<SingleScaledCriterion<OrdinalValue>> aModel) {
        final SingleScaledCriterion<OrdinalValue> criterion = aModel.getObject();
        itsCriterionService.save(criterion);
        WebSession.get().success(getString("message.save.success"));

        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
        setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
