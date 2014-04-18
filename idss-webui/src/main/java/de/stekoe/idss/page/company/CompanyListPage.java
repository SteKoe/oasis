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

package de.stekoe.idss.page.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.component.DataListView;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.DeleteLink;

public class CompanyListPage extends AuthUserPage {

    @Inject
    CompanyService companyService;

    public CompanyListPage(PageParameters pageParameters) {
        super(pageParameters);

        IDataProvider<Company> dataProvider = new IDataProvider<Company>() {

            private List<Company> companiesByUser;

            @Override
            public void detach() {

            }

            @Override
            public Iterator<? extends Company> iterator(long first, long count) {
                getCompanies();
                return companiesByUser.iterator();
            }

            @Override
            public long size() {
                getCompanies();
                return companiesByUser.size();
            }

            private void getCompanies() {
                if(companiesByUser == null) {
                    companiesByUser = companyService.findByUser(WebSession.get().getUser().getId());
                }
            }

            @Override
            public IModel<Company> model(Company object) {
                return new Model(object);
            }
        };

        DataListView<Company> dataListView = new DataListView<Company>("company.list", dataProvider) {
            @Override
            protected List<? extends Link> getButtons(final Company company) {
                List<Link> buttons = new ArrayList<Link>();

                DeleteLink deleteLink = new DeleteLink(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        companyService.delete(company.getId());
                    }
                };
                deleteLink.add(new AttributeAppender("class", " btn-xs"));
                buttons.add(deleteLink);

                buttons.add(new Link(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        // TODO
                    }
                });

                return buttons;
            }
        };
        add(dataListView);
    }
}
