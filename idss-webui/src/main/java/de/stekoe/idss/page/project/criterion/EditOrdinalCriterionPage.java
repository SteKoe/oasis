package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.component.form.criterion.EditOrdinalCriterionForm;
import de.stekoe.idss.page.project.ProjectPage;
import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EditOrdinalCriterionPage extends ProjectPage {
    private static final Logger LOG = Logger.getLogger(EditOrdinalCriterionPage.class);

    private final SingleScaledCriterion itsCriterion = new SingleScaledCriterion();

    public EditOrdinalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue criterionId = aPageParams.get("criterionId");

        add(new EditOrdinalCriterionForm("form", criterionId.toString()));
    }
}
