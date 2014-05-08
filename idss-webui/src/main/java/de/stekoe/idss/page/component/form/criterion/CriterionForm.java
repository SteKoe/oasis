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

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.tinymce.TinyMceBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

public abstract class CriterionForm<T extends MeasurementValue> extends Panel {

    private Form<SingleScaledCriterion<T>> scaleForm;
    private final String criterionId;

    @SpringBean
    private CriterionService criterionService;

    public CriterionForm(String id, String aCriterionId) {
        super(id);
        criterionId = aCriterionId;

        scaleForm();
    }

    public String getCriterionId() {
        return criterionId;
    }

    private void scaleForm() {
        scaleForm = new Form<SingleScaledCriterion<T>>("ordinalScaledCriterionForm", new CompoundPropertyModel<SingleScaledCriterion<T>>(getCriterionModel())) {
            @Override
            protected void onSubmit() {
                onSaveCriterion(getCriterionModel());
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

        final CheckBox allowNoChoiceCheckBox = new CheckBox("allowNoChoice", new PropertyModel(getCriterionModel(), "allowNoChoice"));
        scaleForm.add(allowNoChoiceCheckBox);

        CheckBox checkBox = new CheckBox("allowMultipleChoice", new PropertyModel(getCriterionModel(), "multipleChoice"));
        scaleForm.add(checkBox);
        if(!(getCriterionModel().getObject() instanceof NominalScaledCriterion)) {
            checkBox.setVisible(false);
        }

        scaleForm.add(new MarkRequiredFieldsBehavior());

        add(new SubmitLink("submit.ordinalScaledCriterionForm", scaleForm));
    }

    void moveValueUp(final T value) {
        SingleScaledCriterion<T> criterion = getCriterionModel().getObject();
        criterion.moveUp(value);
        criterionService.saveCriterion(criterion);
        getCriterionModel().detach();
        setResponsePage(getPage());
    }

    void moveValueDown(final T value) {
        SingleScaledCriterion<T> criterion = getCriterionModel().getObject();
        criterion.moveDown(value);
        criterionService.saveCriterion(criterion);
        getCriterionModel().detach();
        setResponsePage(getPage());
    }

    public Form<SingleScaledCriterion<T>> getScaleForm() {
        return scaleForm;
    }

    public abstract IModel<SingleScaledCriterion<T>> getCriterionModel();
    public abstract IModel<T> getValueModel();
    public abstract void onSaveCriterion(IModel<SingleScaledCriterion<T>> aModel);
}
