package de.stekoe.idss.page.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
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

        setTitle(getString("label.company.manage"));

        BookmarkablePageLink<CreateCompanyPage> createCompanyLink = new BookmarkablePageLink<CreateCompanyPage>("link.new", CreateCompanyPage.class);
        add(createCompanyLink);
        createCompanyLink.add(new Label("item", getString("label.company")));

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
