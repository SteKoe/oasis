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

import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;

public abstract class NominalScaledCriterionForm extends CriterionForm<NominalValue> {

    @SpringBean
    private CriterionPageService criterionPageService;

    @SpringBean
    private CriterionService criterionService;

    private Form<NominalValue> valueForm;

    private NominalScaledCriterionModel nominalScaledCriterionModel;
    private final IModel<NominalValue> nominalValueModel = new Model(new NominalValue());

    public NominalScaledCriterionForm(final String aId, final String aCriterionId) {
        super(aId, aCriterionId);
        valueForm();
    }

    private void valueForm() {
        final RequiredTextField<String> valueTextField = new RequiredTextField<String>("value", new PropertyModel<String>(nominalValueModel, "value"));
        valueTextField.add(new Placeholder("Ausprägung..."));
        FormGroup valueGroup = new FormGroup("value.group");
        getScaleForm().add(valueGroup.add(valueTextField));

        final LoadableDetachableModel<List<NominalValue>> valueListModel = new LoadableDetachableModel<List<NominalValue>>() {
            @Override
            protected List<NominalValue> load() {
                return getCriterionModel().getObject().getValues();
            }
        };

        final ListView<NominalValue> valueList = new ListView<NominalValue>("value.list", valueListModel) {
            @Override
            protected void populateItem(final ListItem<NominalValue> item) {
                final NominalValue value = item.getModelObject();
                item.add(new Label("value.list.label", value.getValue()));

                item.add(new Link("value.delete") {
                    @Override
                    public void onClick() {
                        getCriterionModel().getObject().getValues().remove(value);
                        criterionService.saveCriterion(getCriterionModel().getObject());
                        setResponsePage(getPage());
                    }
                });

                item.add(new Link("value.move.up") {
                    @Override
                    public void onClick() {
                        moveValueUp(value);
                    }


                    @Override
                    public boolean isVisible() {
                        return item.getIndex() > 0 && getList().size() > 1;
                    }
                });

                item.add(new Link("value.move.down") {
                    @Override
                    public void onClick() {
                        moveValueDown(value);
                    }

                    @Override
                    public boolean isVisible() {
                        return item.getIndex() < (getList().size()-1) && getList().size() > 1;
                    }
                });
            }
        };
        getScaleForm().add(valueList);

        final WebMarkupContainer emptyListIndicator = new WebMarkupContainer("value.list.empty");
        getScaleForm().add(emptyListIndicator);
        emptyListIndicator.setVisible(valueList.getList().isEmpty());
    }

    @Override
    public IModel<SingleScaledCriterion<NominalValue>> getCriterionModel() {
        if(nominalScaledCriterionModel == null) {
            nominalScaledCriterionModel = new NominalScaledCriterionModel(getCriterionId());
        }
        return nominalScaledCriterionModel;
    }

    @Override
    public IModel<NominalValue> getValueModel() {
        return nominalValueModel;
    }

    class NominalScaledCriterionModel extends Model<SingleScaledCriterion<NominalValue>> {
        private SingleScaledCriterion<NominalValue> criterion;
        private final String id;

        public NominalScaledCriterionModel(String id) {
            this.id = id;
        }

        @Override
        public SingleScaledCriterion<NominalValue> getObject() {
            if(criterion != null) {
                return criterion;
            }

            if(id == null) {
                criterion = new NominalScaledCriterion();
                return criterion;
            } else {
                criterion = criterionService.findSingleScaledCriterionById(getCriterionId());
                return criterion;
            }
        }
    }
}
