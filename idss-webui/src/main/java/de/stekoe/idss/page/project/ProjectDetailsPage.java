/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.project;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Date;

public class ProjectDetailsPage extends ProjectPage {
    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        addDescription();
        addDateInformation();
    }

    private void addDateInformation() {
        final Date projectStartDate = getProject().getProjectStartDate();
        final Date projectEndDate = getProject().getProjectEndDate();

        final Label projectStartDateLabel = new Label("projectStartDate", projectStartDate);
        add(projectStartDateLabel);

        final Label projectEndDateLabel = new Label("projectEndDate", projectEndDate);
        add(projectEndDateLabel);

        double progress = 0;
        if (projectEndDate == null) {
            projectEndDateLabel.setVisible(false);
        } else {
            final Days totalProjectDays = Days.daysBetween(new DateTime(projectStartDate.getTime()), new DateTime(projectEndDate.getTime()));
            final Days passedDays = Days.daysBetween(new DateTime(projectStartDate.getTime()), new DateTime());

            if (passedDays.getDays() > 0) {
                progress = (double) passedDays.getDays() / (double) totalProjectDays.getDays();
                progress *= 100;
            }
        }

        final WebMarkupContainer progressBar = new WebMarkupContainer("progressBar");
        progressBar.add(new AttributeModifier("style", "width: " + Math.round(progress) + "%"));
        add(progressBar);

        progressBar.add(new Label("progress", Math.round(progress)));
    }

    private void addDescription() {
        String description = getProject().getDescription();
        if (StringUtils.isEmpty(description)) {
            description = getString("label.description.na");
        }
        final Label descriptionText = new Label("descriptionText", Model.of(description));
        add(descriptionText);
        descriptionText.setEscapeModelStrings(false);
    }

}
