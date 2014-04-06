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

package de.stekoe.idss.page.project.criterion.page;

import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;

public class CriteriaPageDetailsPage extends ProjectPage {
    @SpringBean
    private CriterionPageService criterionPageService;

    private List<CriterionPage> criterionPages;
    private LoadableDetachableModel<CriterionPage> criterionPageModel;

    public CriteriaPageDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue pageIdParam = pageParameters.get("pageId");
        criterionPageModel = new LoadableDetachableModel<CriterionPage>() {
            @Override
            protected CriterionPage load() {
                return criterionPageService.findOne(pageIdParam.toString());
            }
        };

        if (criterionPageModel.getObject() == null) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(CriteriaPageListPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        if (!getProjectId().equals(criterionPageModel.getObject().getProject().getId())) {
            WebSession.get().error(getString("message.error.404"));
            setResponsePage(CriteriaPageListPage.class, new PageParameters().add("projectId", getProjectId()));
            return;
        }

        criterionPages = criterionPageService.getCriterionPagesForProject(criterionPageModel.getObject().getProject().getId());

        addPageElements();

        addPrevPageLink();
        addCurrentPageLink();
        addNextPageLink();
    }

    private void addPageElements() {
        add(new ListView<PageElement>("page.elements", criterionPageModel.getObject().getPageElements()) {
            @Override
            protected void populateItem(ListItem<PageElement> item) {
                PageElement pageElement = (PageElement) item.getDefaultModelObject();

                String wicketId = "page.element.type";
                if (pageElement instanceof OrdinalScaledCriterion) {
                    item.add(new OrdinalScaledCriterionFormElement(wicketId, (OrdinalScaledCriterion) pageElement));
                } else if (pageElement instanceof NominalScaledCriterion) {
                    item.add(new NominalScaledCriterionFormElement(wicketId, (NominalScaledCriterion) pageElement));
                } else {
                    item.add(new Label(wicketId, item.getDefaultModelObject().getClass().getName()));
                }
            }
        });
    }

    private void addCurrentPageLink() {
        add(new ListView<CriterionPage>("page.list", criterionPages) {
            @Override
            protected void populateItem(ListItem<CriterionPage> item) {
                final CriterionPage criterionPage = item.getModelObject();

                final PageParameters parameters = new PageParameters(getPageParameters());
                parameters.set("pageId", criterionPage.getId());

                final BookmarkablePageLink<CriteriaPageDetailsPage> pageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.link", CriteriaPageDetailsPage.class, parameters);
                item.add(pageLink);
                pageLink.setBody(Model.of(criterionPage.getOrdering() + 1));
                if (criterionPageModel.getObject().getId().equals(criterionPage.getId())) {
                    item.add(AttributeModifier.append("class", "active"));
                }
            }
        });
    }

    private void addPrevPageLink() {
        final boolean hasPrevPage = criterionPageModel.getObject().getOrdering() > 1;

        final WebMarkupContainer component = new WebMarkupContainer("page.prev");
        add(component);

        final PageParameters parameters = new PageParameters(getPageParameters());
        if (hasPrevPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() - 1).getId());
        } else {
            component.add(AttributeModifier.append("class", "disabled"));
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> prevPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.prev.link", CriteriaPageDetailsPage.class, parameters);
        component.add(prevPageLink);
    }

    private void addNextPageLink() {
        final boolean hasNextPage = criterionPageModel.getObject().getOrdering() < criterionPages.size() - 1;

        final WebMarkupContainer component = new WebMarkupContainer("page.next");
        add(component);

        final PageParameters parameters = new PageParameters(getPageParameters());
        if (hasNextPage) {
            parameters.set("pageId", criterionPages.get(criterionPageModel.getObject().getOrdering() + 1).getId());
        } else {
            component.add(AttributeModifier.append("class", "disabled"));
        }

        final BookmarkablePageLink<CriteriaPageDetailsPage> nextPageLink = new BookmarkablePageLink<CriteriaPageDetailsPage>("page.next.link", CriteriaPageDetailsPage.class, parameters);
        component.add(nextPageLink);
    }
}
