package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.component.form.criterion.CreateOrdinalScaledCriterionForm;
import de.stekoe.idss.page.project.ProjectPage;
import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateOrdinalCriterionPage extends ProjectPage {
    private static final Logger LOG = Logger.getLogger(CreateOrdinalCriterionPage.class);

    private final SingleScaledCriterion itsCriterion = new SingleScaledCriterion();

    public CreateOrdinalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue pageId = aPageParams.get("pageId");

        add(new CreateOrdinalScaledCriterionForm("form", pageId.toString()));
    }
}
