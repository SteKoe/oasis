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
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.repository.UserChoiceRepository;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.session.WebSession;

public class SurveyPage extends ProjectPage {

    private static final Logger LOG = Logger.getLogger(SurveyPage.class);

    @Inject
    private CriterionPageService criterionPageService;

    @Inject
    private UserChoiceRepository userChoiceRepository;

    private final List<CriterionPage> criterionPages;

    public SurveyPage(PageParameters pageParameters) {
        super(pageParameters);
        criterionPages = criterionPageService.findAllForProject(getProjectId());

        // ====
        IDataProvider<CriterionPage> dataProvider = new IDataProvider<CriterionPage>() {
            @Override
            public void detach() {
            }

            @Override
            public Iterator<? extends CriterionPage> iterator(long first, long count) {
                List<CriterionPage> page = new ArrayList<CriterionPage>();
                page.add(criterionPages.get((int) first));
                return page.iterator();
            }

            @Override
            public long size() {
                return criterionPages.size();
            }

            @Override
            public IModel<CriterionPage> model(CriterionPage object) {
                return new Model(object);
            }
        };


        final CriterionPagePanel criterionPagePanel;
        final SurveyPageDataView dataView = new SurveyPageDataView("criterionDataList", dataProvider, 1);

        Form form = new Form("survey.form") {
            @Override
            protected void onSubmit() {
                super.onSubmit();

                Iterator<PageElementPanel> iterator = dataView.getCriterionPagePanels().iterator();
                while(iterator.hasNext()) {
                    PageElementPanel next = iterator.next();

                    IModel<UserChoice> model = next.getModel();
                    UserChoice choice = model.getObject();
                    choice.setUser(WebSession.get().getUser());
                    choice.setProject(dataView.getCriterionPage().getProject());

                    userChoiceRepository.save(choice);
                }

                setResponsePage(getPage());
            }
        };
        add(form);

        form.add(new Button("btn.prev"){
            @Override
            public void onSubmit() {
                long currentPage = dataView.getCurrentPage();
                if(currentPage > 0) {
                    dataView.setCurrentPage(currentPage - 1);
                }
            }

            @Override
            public boolean isEnabled() {
                return dataView.getCurrentPage() > 0;
            }
        });

        form.add(new Button("btn.next"){
            @Override
            public void onSubmit() {
                long currentPage = dataView.getCurrentPage();
                long pageCount = dataView.getPageCount();
                if(currentPage < pageCount-1) {
                    dataView.setCurrentPage(currentPage + 1);
                }
            }

            @Override
            public boolean isEnabled() {
                long currentPage = dataView.getCurrentPage();
                long pageCount = dataView.getPageCount();
                return currentPage < pageCount-1;
            }
        });

        form.add(dataView);
    }

    private class SurveyPageDataView extends DataView<CriterionPage> {
        protected SurveyPageDataView(String id, IDataProvider<CriterionPage> dataProvider, long itemsPerPage) {
            super(id, dataProvider, itemsPerPage);
        }

        private CriterionPagePanel criterionPagePanel;
        private CriterionPage criterionPage;

        @Override
        protected void populateItem(Item<CriterionPage> item) {
            criterionPage = item.getModelObject();

            criterionPagePanel = new CriterionPagePanel("criterionPagePanel", criterionPages, getCriterionPage());
            item.add(criterionPagePanel);
        }

        public List<PageElementPanel> getCriterionPagePanels() {
            return criterionPagePanel.getPageElementPanels();
        }

        public CriterionPage getCriterionPage() {
            return criterionPage;
        }
    }
}
