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

import java.util.List;

import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.tinymce.TinyMceBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.criterion.CriterionPageElementId;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.Orderable;
import de.stekoe.idss.service.ScaleService;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class OrdinalScaledCriterionForm extends Panel {

    @SpringBean
    private CriterionService criterionService;

    @SpringBean
    private CriterionPageService criterionPageService;

    @SpringBean
    private ScaleService scaleService;

    private Form<SingleScaledCriterion> scaleForm;
    private Form<OrdinalValue> valueForm;
    private final CriterionPageElementId criterionId;

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

    public OrdinalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId);
        criterionId = new CriterionPageElementId(aCriterionId);

        scaleForm();
        valueForm();
    }

    /**
     * Overall form for the scale
     */
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

    /**
     * Subform for just the values of the scale
     */
    private void valueForm() {
        valueForm = new Form<OrdinalValue>("valueForm", new CompoundPropertyModel<OrdinalValue>(new OrdinalValue())) {
            @Override
            protected void onSubmit() {
                final SingleScaledCriterion criterion = itsCriterionModel.getObject();
                final OrdinalValue value = getModel().getObject();

                criterionService.addValue(criterion, value);
                onSaveCriterion(itsCriterionModel);
            }
        };
        add(valueForm);

        final RequiredTextField<String> valueTextField = new RequiredTextField<String>("value");
        FormGroup valueGroup = new FormGroup("value.group");
        valueForm.add(valueGroup.add(valueTextField));

        final RequiredTextField<Double> rankTextField = new RequiredTextField<Double>("rank");
        valueForm.add(valueGroup.add(rankTextField));

        final LoadableDetachableModel<List<OrdinalValue>> valueListModel = new LoadableDetachableModel<List<OrdinalValue>>() {
            @Override
            protected List<OrdinalValue> load() {
                final Scale scale = itsCriterionModel.getObject().getScale();
                return scale.getValues();
            }
        };

        final ListView<OrdinalValue> valueList = new ListView<OrdinalValue>("value.list", valueListModel) {
            @Override
            protected void populateItem(final ListItem<OrdinalValue> item) {
                final OrdinalValue value = item.getModelObject();
                item.add(new Label("value.list.label", value.getValue() + " (" + value.getRank() + ")"));

                item.add(new Link("value.delete") {
                    @Override
                    public void onClick() {
                        criterionService.deleteValue(value);
                        setResponsePage(getPage());
                    }
                });

                item.add(new Link("value.move.up") {
                    @Override
                    public void onClick() {
                        scaleService.move(value, Orderable.Direction.UP);
                        itsCriterionModel.detach();
                        valueListModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return item.getIndex() > 0 && getList().size() > 1;
                    }
                });

                item.add(new Link("value.move.down") {
                    @Override
                    public void onClick() {
                        scaleService.move(value, Orderable.Direction.DOWN);
                        itsCriterionModel.detach();
                        valueListModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return item.getIndex() < (getList().size()-1) && getList().size() > 1;
                    }
                });
            }
        };
        valueForm.add(valueList);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("value.list.empty");
        valueForm.add(emptyListIndicator);
        emptyListIndicator.setVisible(valueList.getList().isEmpty());
    }

    public abstract void onSaveCriterion(IModel<SingleScaledCriterion> aModel);
}
