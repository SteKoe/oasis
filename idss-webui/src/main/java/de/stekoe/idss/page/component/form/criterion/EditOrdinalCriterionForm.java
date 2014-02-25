package de.stekoe.idss.page.component.form.criterion;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EditOrdinalCriterionForm extends OrdinalScaledCriterionForm {

    @SpringBean
    private CriterionService itsCriterionService;

    public EditOrdinalCriterionForm(String aId, String aCriterionId) {
        super(aId, aCriterionId);
    }

    @Override
    public void onSave(IModel<SingleScaledCriterion> aModel) {
        final SingleScaledCriterion criterion = aModel.getObject();
        itsCriterionService.save(criterion);
        setResponsePage(EditOrdinalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", criterion.getId()));
    }
}
