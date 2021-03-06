package de.stekoe.idss.page.project.criterion;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public class CreateOrdinalScaledCriterionForm extends OrdinalScaledCriterionForm {

    @Inject
    private CriterionService criterionService;

    @Inject
    private CriterionPageService criterionPageService;

    @Inject
    private ProjectService projectService;

    private final String itsPageId;

    public CreateOrdinalScaledCriterionForm(String aId, String aPageId) {
        super(aId, null);
        this.itsPageId = aPageId;
    }

    @Override
    public void onSaveCriterion(IModel<SingleScaledCriterion<OrdinalValue>> aModel) {
        final CriterionPage page = criterionPageService.findOne(itsPageId);

        final SingleScaledCriterion<OrdinalValue> criterion = aModel.getObject();
        if(StringUtils.isEmpty(criterion.getName())) {
            criterion.setName(getString("label.criterion.type.ordinal"));
        }
        criterion.setCriterionPage(page);
        criterionPageService.save(page);

        WebSession.get().success(getString("message.save.success"));

        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
        setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
