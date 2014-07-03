package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.page.project.ProjectPage;

public class CreateNominalCriterionPage extends ProjectPage {
    public CreateNominalCriterionPage(PageParameters aPageParams) {
        super(aPageParams);

        final StringValue pageId = aPageParams.get("pageId");

        add(new CreateNominalScaledCriterionForm("form", pageId.toString()));
    }
}
