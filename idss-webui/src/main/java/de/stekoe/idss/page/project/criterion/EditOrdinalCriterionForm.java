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

package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.OrdinalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.session.WebSession;

public class EditOrdinalCriterionForm extends OrdinalScaledCriterionForm {

    @SpringBean
    private CriterionService itsCriterionService;

    public EditOrdinalCriterionForm(String aId, String aCriterionId) {
        super(aId, aCriterionId);
    }

    @Override
    public void onSaveCriterion(IModel<SingleScaledCriterion<OrdinalValue>> aModel) {
        final SingleScaledCriterion<OrdinalValue> criterion = aModel.getObject();
        itsCriterionService.save(criterion);
        WebSession.get().success(getString("message.save.success"));

        final PageParameters pageParams = new PageParameters().add("criterionId", criterion.getId());
        setResponsePage(EditOrdinalCriterionPage.class, getPage().getPageParameters().mergeWith(pageParams));
    }
}
