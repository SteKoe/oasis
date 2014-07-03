package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.page.project.ProjectPage;

/**
 * @author Stephan Koeninger 
 */
public class CreateOrdinalCriterionPage extends ProjectPage {
    public CreateOrdinalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue pageId = aPageParams.get("pageId");

        add(new CreateOrdinalScaledCriterionForm("form", pageId.toString()));
    }
}
