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
