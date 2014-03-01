package de.stekoe.idss.page.component.form.criterion;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateOrdinalScaledCriterionForm extends OrdinalScaledCriterionForm {

    @SpringBean
    private CriterionService itsCriterionService;

    @SpringBean
    private CriterionPageService itsCriterionPageService;

    private final String itsPageId;

    public CreateOrdinalScaledCriterionForm(String aId, String aPageId) {
        super(aId, null);
        this.itsPageId = aPageId;
    }

    @Override
    public void onSave(IModel<SingleScaledCriterion> aModel) {
        final CriterionPage page = itsCriterionPageService.findById(itsPageId);

        final SingleScaledCriterion criterion = aModel.getObject();
        criterion.setOrdering(page.getPageElements().size() + 1);
        itsCriterionService.save(criterion);

        page.getPageElements().add(criterion);
        itsCriterionPageService.save(page);

        getWebSession().success("Success");

        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
        setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
