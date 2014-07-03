package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.page.project.ProjectPage;

/**
 * @author Stephan Koeninger 
 */
public class EditOrdinalCriterionPage extends ProjectPage {
    public EditOrdinalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue criterionId = aPageParams.get("criterionId");

        add(new EditOrdinalCriterionForm("form", criterionId.toString()));
    }
}
