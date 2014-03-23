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

package de.stekoe.idss.page.project.criterion.page;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.CriterionPageElement;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.criterion.SelectCriterionPage;
import de.stekoe.idss.page.project.criterion.page.element.SingleScaledCriterionElement;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CriteriaPageListPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    @SpringBean
    private CriterionPageService criterionPageService;

    private final LoadableDetachableModel<List<CriterionPage>> loadableDetachableModel = new LoadableDetachableModel<List<CriterionPage>>() {
        @Override
        protected List<CriterionPage> load() {
            return criterionPageService.getCriterionPagesForProject(getProjectId());
        }
    };


    public CriteriaPageListPage(PageParameters pageParameters) {
        super(pageParameters);

        addListPageView();
        addNewPageButton();
    }

    private void addListPageView() {
        final ListView<CriterionPage> listView = new ListView<CriterionPage>("page.list", loadableDetachableModel) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();
                item.add(new Label("id", criterionPage.getId()));
                item.add(new Label("order", criterionPage.getOrdering()));

                item.add(movePageUpLink(criterionPage));
                item.add(movePageDownLink(criterionPage));

                item.add(new BookmarkablePageLink<CriteriaPageDetailsPage>("page.show", CriteriaPageDetailsPage.class, new PageParameters(getPageParameters()).add("pageId", criterionPage.getId())));
                item.add(deletePageLink(criterionPage));
                item.add(new BookmarkablePageLink<SelectCriterionPage>("page.add.criterion", SelectCriterionPage.class, new PageParameters(getPageParameters()).add("pageId", criterionPage.getId())));

                final ListView<CriterionPageElement> pageListItems = new CriterionPageElementListView("page.list.items", item.getModelObject().getPageElements());
                item.add(pageListItems);

                final WebMarkupContainer emptyTable = new WebMarkupContainer("page.empty");
                item.add(emptyTable);
                emptyTable.setVisible(pageListItems.getList().isEmpty());
            }

            private Link movePageUpLink(final CriterionPage criterionPage) {
                final Link link = new Link("move.page.up") {
                    @Override
                    public void onClick() {
                        criterionPageService.move(criterionPage, CriterionPageService.Direction.UP);
                        loadableDetachableModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() > 1);
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.move.up")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }

            private Link movePageDownLink(final CriterionPage criterionPage) {
                final Link link = new Link("move.page.down") {
                    @Override
                    public void onClick() {
                        criterionPageService.move(criterionPage, CriterionPageService.Direction.DOWN);
                        loadableDetachableModel.detach();
                        setResponsePage(getPage());
                    }

                    @Override
                    public boolean isVisible() {
                        return (criterionPage.getOrdering() < loadableDetachableModel.getObject().size());
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.move.down")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }

            private Link deletePageLink(final CriterionPage criterionPage) {
                final Link link = new Link("page.delete") {
                    @Override
                    public void onClick() {
                        criterionPageService.delete(criterionPage.getId());
                        setResponsePage(getPage());
                    }
                };
                link.add(AttributeModifier.append("title", getString("label.delete")));
                link.add(AttributeModifier.append("data-toggle", "tooltip"));
                return link;
            }
        };
        add(listView);
    }

    private void addNewPageButton() {
        final Link<Void> newPageButton = new Link<Void>("add.page") {
            @Override
            public void onClick() {
                CriterionPage criterionPage = new CriterionPage();
                criterionPage.setProject(getProject());
                criterionPage.setOrdering(criterionPageService.getNextPageNumForProject(getProjectId()));
                criterionPageService.save(criterionPage);

                setResponsePage(getPage());
            }
        };
        add(newPageButton);
    }

    private class CriterionPageElementListView extends ListView<CriterionPageElement> {

        public CriterionPageElementListView(String id, List<? extends CriterionPageElement> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<CriterionPageElement> item) {
            final CriterionPageElement criterionPageElement = item.getModelObject();
            if (criterionPageElement instanceof SingleScaledCriterion) {
                SingleScaledCriterion ssc = (SingleScaledCriterion) criterionPageElement;
                item.add(new SingleScaledCriterionElement("page.list.item", ssc));
            }
        }
    }
}
