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

import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageListPage;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ReferenceCriterionGroupService;

public class SelectReferenceCriterionPage extends ProjectPage {
    private static final Logger LOG = Logger.getLogger(SelectReferenceCriterionPage.class);

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    CriterionPageService criterionPageService;

    private List<CriterionGroup> selectedGroups = new ArrayList<CriterionGroup>();
    private CriterionPage criterionPage;

    public SelectReferenceCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        String criterionPageId = pageParameters.get("pageId").toString(null);
        if(criterionPageId != null) {
            criterionPage = criterionPageService.findOne(criterionPageId);
        }

        ArrayList<CriterionGroup> referenceGroups = (ArrayList<CriterionGroup>) referenceCriterionGroupService.findAll();

        Form form = new Form("form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                int pageNum = criterionPageService.getNextPageNumForProject(getProjectId());

                List<CriterionGroup> selectedCritionGroups = getSelectedGroups();
                for (CriterionGroup criterionGroup : selectedCritionGroups) {
                    criterionGroup.setCriterionPage(criterionPage);
                    criterionPage.getPageElements().add(criterionGroup);
                    criterionGroup.setOrdering(pageNum);
                    pageNum++;

                    int order = 0;
                    for(Criterion criterion : criterionGroup.getCriterions()) {
                        criterion.setCriterionPage(criterionPage);
                        criterionPage.getPageElements().add(criterion);
                        criterion.setOrdering(order);
                        order++;
                    }

                    criterionGroupService.save(criterionGroup);
                    criterionPageService.save(criterionPage);
                }

                setResponsePage(CriteriaPageListPage.class, getPageParameters());
            }
        };
        add(form);

        // ListView of Reference CriterionGroups with its ReferenceCriterions
        ListView<CriterionGroup> listView = new ListView<CriterionGroup>("panel", new Model<ArrayList<CriterionGroup>>(referenceGroups)) {
            @Override
            protected void populateItem(ListItem<CriterionGroup> item) {
                CriterionGroup criterionGroup = item.getModelObject();

                WebMarkupContainer panelTitle = new WebMarkupContainer("panel.title");
                item.add(panelTitle);
                panelTitle.add(new AttributeModifier("href", "#collapse" + item.getIndex()));

                panelTitle.add(new Label("criteriongroup.name", criterionGroup.getName()));

                WebMarkupContainer panelCollapse = new WebMarkupContainer("panel.collapse");
                item.add(panelCollapse);
                panelCollapse.add(new AttributeModifier("id", "collapse" + item.getIndex()));

                WebMarkupContainer panelBody = new WebMarkupContainer("panel.body");
                panelCollapse.add(panelBody);

                panelBody.add(criterionList(criterionGroup));
            }

            private Component criterionList(final CriterionGroup criterionGroup) {
                List<Criterion> criterions = criterionGroup.getCriterions();
                return new ListView<Criterion>("criterion.list", criterions) {
                    @Override
                    protected void populateItem(ListItem<Criterion> item) {
                        Criterion citerion = item.getModelObject();
                        item.add(new CheckBox("criterion.checkbox", new SelectReferenceCriterionModel(criterionGroup, citerion, getSelectedGroups())));
                        item.add(new Label("criterion.name", citerion.getName()));
                    }
                };
            }
        };
        form.add(listView);
    }

    public List<CriterionGroup> getSelectedGroups() {
        return selectedGroups;
    }

    public void setSelectedGroups(List<CriterionGroup> selectedGroups) {
        this.selectedGroups = selectedGroups;
    }
}
