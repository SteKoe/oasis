/*
 * Copyright 2014 Stephan Koeninger
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

package de.stekoe.idss.page.component.form.criterion;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;

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
        criterion.setOrdering(page.getPageElements().size() + 1);

        criterionService.saveCriterion(criterion);

        page.getPageElements().add(criterion);
        criterionPageService.save(page);

        Project project = page.getProject();
        project.getScaleList().add(criterion);
        projectService.save(project);

        getWebSession().success("Success");

        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
        setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
