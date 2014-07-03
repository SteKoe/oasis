package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.session.WebSession;

public class EditNominalCriterionForm extends NominalScaledCriterionForm {

    @SpringBean
    private CriterionService itsCriterionService;

    public EditNominalCriterionForm(String aId, String aCriterionId) {
        super(aId, aCriterionId);
    }

    @Override
    public void onSaveCriterion(IModel<SingleScaledCriterion<NominalValue>> aModel) {
        final SingleScaledCriterion<NominalValue> criterion = aModel.getObject();
        itsCriterionService.save(criterion);
        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());

        WebSession.get().success(getString("message.save.success"));
        setResponsePage(EditNominalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
