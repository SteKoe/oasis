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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.ReferenceCriterionGroupService;

public class SelectReferenceCriterionPage extends ProjectPage {

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    public SelectReferenceCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        ArrayList<CriterionGroup> referenceGroups = (ArrayList<CriterionGroup>) referenceCriterionGroupService.findAll();
        ListView<CriterionGroup> listView = new ListView<CriterionGroup>("panel", new Model<ArrayList<CriterionGroup>>(referenceGroups)) {
            @Override
            protected void populateItem(ListItem<CriterionGroup> item) {

                Label panelTitle = new Label("panel.title", item.getModelObject().getName());
                item.add(panelTitle);
                panelTitle.add(new AttributeModifier("href", "#collapse" + item.getIndex()));

                WebMarkupContainer panelCollapse = new WebMarkupContainer("panel.collapse");
                item.add(panelCollapse);
                panelCollapse.add(new AttributeModifier("id", "collapse" + item.getIndex()));

                WebMarkupContainer panelBody = new WebMarkupContainer("panel.body");
                panelCollapse.add(panelBody);

                panelBody.add(criterionList(item));
            }

            private Component criterionList(ListItem<CriterionGroup> item) {
                List<Criterion> criterions = item.getModelObject().getCriterions();
                return new ListView<Criterion>("criterion.list", criterions) {
                    @Override
                    protected void populateItem(ListItem<Criterion> item) {
                        item.add(new Label("criterion.name", item.getModelObject().getName()));
                    }
                };
            }
        };
        add(listView);
    }
}
