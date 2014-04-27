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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.WebApplication;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.session.WebSession;

public abstract class CompanyPage extends AuthUserPage {

    @SpringBean
    private CompanyService companyService;

    private String companyId;

    private CompanyModel companyModel;

    public CompanyPage(PageParameters pageParameters) {
        super(pageParameters);

        StringValue companyIdParam = pageParameters.get("companyId");
        if(companyIdParam.isEmpty()) {
            setResponsePage(WebApplication.HOMEPAGE);
            return;
        } else {
            this.companyId = companyIdParam.toString();
        }

        if(!companyService.isAuthorized(WebSession.get().getUser().getId(), this.companyId, PermissionType.READ)) {
            setResponsePage(WebApplication.HOMEPAGE);
            return;
        }

        companyModel = new CompanyModel(companyId);

        add(new ListView<Link>("links", getLinks()) {
            @Override
            protected void populateItem(ListItem<Link> item) {
                item.add(item.getModelObject());
            }
        });
    }

    private List<? extends Link> getLinks() {
        List<BookmarkablePageLink> links = new ArrayList<BookmarkablePageLink>();

        BookmarkablePageLink<CompanyDetailsPage> companyDetails = new BookmarkablePageLink<CompanyDetailsPage>("link", CompanyDetailsPage.class, getPageParameters());
        companyDetails.setBody(Model.of(getString("label.overview")));
        links.add(companyDetails);

        BookmarkablePageLink<EmployeeListPage> employees = new BookmarkablePageLink<EmployeeListPage>("link", EmployeeListPage.class, getPageParameters());
        employees.setBody(Model.of(getString("label.company.employees.manage")));
        links.add(employees);


        BookmarkablePageLink<CompanyRoleListPage> companyRoles = new BookmarkablePageLink<CompanyRoleListPage>("link", CompanyRoleListPage.class, getPageParameters());
        companyRoles.setBody(Model.of(getString("label.company.roles.manage")));
        links.add(companyRoles);

        BookmarkablePageLink<CompanyAddressesListPage> companyAddresses = new BookmarkablePageLink<CompanyAddressesListPage>("link", CompanyAddressesListPage.class, getPageParameters());
        companyAddresses.setBody(Model.of(getString("label.company.addresses.manage")));
        links.add(companyAddresses);

        return links;
    }

    Company getCompany() {
        if(companyModel == null) {
            companyModel = new CompanyModel(companyId);
        }
        return (Company) companyModel.getObject();
    }

    CompanyModel getCompanyModel() {
        return companyModel;
    }

    private class CompanyModel extends Model {
        private String id;
        private Company company;

        public CompanyModel(String id) {
            this.id = id;
        }

        public CompanyModel(Company object) {
            setObject(object);
        }

        @Override
        public Serializable getObject() {
            if(company != null) {
                return company;
            }

            if(id == null) {
                return new Company();
            } else {
                company = companyService.findOne(id);
                return company;
            }
        }

        @Override
        public void setObject(Serializable object) {
            company = (Company) object;
            id = (object != null) ? company.getId() : null;
        }

        @Override
        public void detach() {
            company = null;
        }
    }
}
