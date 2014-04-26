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

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Size;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.PermissionType;
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

        add(new BookmarkablePageLink<CreateCompanyPage>("link.new", CreateCompanyPage.class));

        IDataProvider<Company> dataProvider = new IDataProvider<Company>() {

            private List<Company> companies;

            @Override
            public void detach() {
                this.companies = null;
            }

            @Override
            public Iterator<? extends Company> iterator(long first, long count) {
                getCompanies();
                return companies.iterator();
            }

            @Override
            public long size() {
                getCompanies();
                return companies.size();
            }

            private void getCompanies() {
                if(companies == null) {
                    if(WebSession.get().getUser().isAdmin()) {
                        companies = companyService.findAll();
                    } else {
                        companies = companyService.findByUser(WebSession.get().getUser().getId());
                    }
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

                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("companyId", company.getId());

                // Details link
                BookmarkablePageLink<CompanyDetailsPage> detailsPage = new BookmarkablePageLink<CompanyDetailsPage>(DataListView.BUTTON_ID, CompanyDetailsPage.class, pageDetailsParameters);
                detailsPage.add(new ButtonBehavior(Type.Default, Size.Mini));
                detailsPage.setBody(Model.of(getString("label.details")));
                buttons.add(detailsPage);

                // Delete
                DeleteLink deleteLink = new DeleteLink(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        companyService.delete(company.getId());
                        WebSession.get().success(getString("message.delete.success"));
                        setResponsePage(getPage());
                    }
                };
                deleteLink.add(new ButtonBehavior(Size.Mini));
                deleteLink.setVisible(companyService.isAuthorized(WebSession.get().getUser().getId(), company.getId(), PermissionType.DELETE));
                buttons.add(deleteLink);

                return buttons;
            }
        };
        add(dataListView);
    }
}
