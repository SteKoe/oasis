package de.stekoe.idss.page.project;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class ProjectDetailsPage extends ProjectPage {
    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        // Description
        String description = getProject().getDescription();
        if(StringUtils.isEmpty(description)) {
            description = getString("label.description.na");
        }
        final Label descriptionText = new Label("descriptionText", Model.of(description));
        add(descriptionText);
        descriptionText.setEscapeModelStrings(false);
    }

}
