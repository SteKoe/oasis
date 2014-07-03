package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.page.project.ProjectPage;

/**
 * @author Stephan Koeninger 
 */
public class EditNominalCriterionPage extends ProjectPage {
    public EditNominalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue criterionId = aPageParams.get("criterionId");
        add(new EditNominalCriterionForm("form", criterionId.toString()));
    }
}
