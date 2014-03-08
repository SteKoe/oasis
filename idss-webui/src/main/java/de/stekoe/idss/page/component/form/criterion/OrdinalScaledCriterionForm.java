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
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class OrdinalScaledCriterionForm extends Panel {

    @SpringBean
    private CriterionService itsCriterionService;

    @SpringBean
    private CriterionPageService itsCriterionPageService;

    private LoadableDetachableModel<SingleScaledCriterion> itsCriterionModel;
    private Form<SingleScaledCriterion> scaleForm;
    private Form<OrdinalValue> valueForm;

    public OrdinalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId);
        itsCriterionModel = new LoadableDetachableModel<SingleScaledCriterion>() {
            @Override
            protected SingleScaledCriterion load() {
                if (aCriterionId == null) {
                    SingleScaledCriterion criterion = new SingleScaledCriterion();
                    criterion.setScale(new OrdinalScale());
                    return criterion;
                } else {
                    return (SingleScaledCriterion) itsCriterionService.findById(aCriterionId);
                }
            }
        };

        scaleForm();
        valueForm();
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
        nameTextField.setLabel(Model.of(getString("label.name")));
        nameTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("name.group").add(nameTextField));

        final TextField<String> descriptionTextField = new TextField<String>("description");
        descriptionTextField.setLabel(Model.of(getString("label.description")));
        descriptionTextField.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        descriptionTextField.add(new PropertyValidator<String>());
        scaleForm.add(new FormGroup("description.group").add(descriptionTextField));

        final CheckBox allowNoChoiceCheckBox = new CheckBox("allowNoChoice");
        scaleForm.add(allowNoChoiceCheckBox);


        scaleForm.add(new MarkRequiredFieldsBehavior());

        add(new SubmitLink("submit.ordinalScaledCriterionForm", scaleForm));
    }

    private void valueForm() {
        valueForm = new Form<OrdinalValue>("valueForm", new CompoundPropertyModel<OrdinalValue>(new OrdinalValue())) {
            @Override
            protected void onSubmit() {
                final SingleScaledCriterion criterion = itsCriterionModel.getObject();
                criterion.setName(StringUtils.isBlank(criterion.getName()) ? getString("label.criterion.type.ordinal") : criterion.getName());

                final OrdinalValue value = getModel().getObject();
                value.setRank(criterion.getScale().getValues().size() + 1);
                criterion.getScale().getValues().add(value);

                itsCriterionModel.setObject(criterion);
                onSaveCriterion(itsCriterionModel);
            }
        };
        add(valueForm);

        final RequiredTextField<String> valueTextField = new RequiredTextField<String>("value");
        valueForm.add(new FormGroup("value.group").add(valueTextField));

        final ListView<OrdinalValue> valueList = new ListView<OrdinalValue>("value.list", itsCriterionModel.getObject().getScale().getValues()) {
            @Override
            protected void populateItem(ListItem<OrdinalValue> item) {
                final OrdinalValue value = item.getModelObject();
                item.add(new Label("value.list.label", value.getValue() + " (" + value.getRank() + ")"));

                item.add(new Link("value.move.up") {
                    @Override
                    public void onClick() {
                    }
                });
                item.add(new Label("value.move.down", ""));
            }
        };
        valueForm.add(valueList);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("value.list.empty");
        valueForm.add(emptyListIndicator);
        emptyListIndicator.setVisible(valueList.getList().isEmpty());
    }

    public abstract void onSaveCriterion(IModel<SingleScaledCriterion> aModel);
}
