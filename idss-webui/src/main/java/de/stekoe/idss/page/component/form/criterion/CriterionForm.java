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

package de.stekoe.idss.page.component.form.criterion;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.criterion.CriterionPageElementId;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

public abstract class CriterionForm extends Panel {

    private Form<SingleScaledCriterion> scaleForm;
    private final CriterionPageElementId criterionId;

    @SpringBean
    private CriterionService criterionService;

    private final LoadableDetachableModel<SingleScaledCriterion> itsCriterionModel = new LoadableDetachableModel<SingleScaledCriterion>() {
        @Override
        protected SingleScaledCriterion load() {
            if (criterionId.getId() == null) {
                SingleScaledCriterion criterion = new SingleScaledCriterion();
                final OrdinalScale scale = new OrdinalScale();
                criterion.setScale(scale);
                scale.setCriterion(criterion);
                return criterion;
            } else {
                return criterionService.findSingleScaledCriterionById(criterionId);
            }
        }
    };

    public CriterionForm(String id, String aCriterionId) {
        super(id);
        criterionId = new CriterionPageElementId(aCriterionId);

        scaleForm();
    }

    private void scaleForm() {
        scaleForm = new Form<SingleScaledCriterion>("ordinalScaledCriterionForm", new CompoundPropertyModel<SingleScaledCriterion>(itsCriterionModel)) {
            @Override
            protected void onSubmit() {
                onSaveCriterion(getModel());
            }
        };
        add(scaleForm);


        final TextField<String> nameTextField = new TextField<String>("name");
        nameTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("name.group").add(nameTextField));

        final TextField<String> descriptionTextField = new TextField<String>("description");
        descriptionTextField.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        descriptionTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("description.group").add(descriptionTextField));

        final CheckBox allowNoChoiceCheckBox = new CheckBox("allowNoChoice");
        scaleForm.add(allowNoChoiceCheckBox);


        scaleForm.add(new MarkRequiredFieldsBehavior());

        add(new SubmitLink("submit.ordinalScaledCriterionForm", scaleForm));
    }

    protected LoadableDetachableModel<SingleScaledCriterion> getCriterionModel() {
        return itsCriterionModel;
    }

    public abstract void onSaveCriterion(IModel<SingleScaledCriterion> aModel);
}
