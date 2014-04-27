package de.stekoe.idss.page.company;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.session.WebSession;

public class CompanyRoleListPage extends CompanyPage {
    public CompanyRoleListPage(PageParameters pageParameters) {
        super(pageParameters);

        final Employee employee = getCompany().getEmployee(WebSession.get().getUser());

        add(new ListView<CompanyRole>("roles", getCompany().getRoles()) {
            @Override
            protected void populateItem(ListItem<CompanyRole> item) {
                CompanyRole role = item.getModelObject();

                //
                Label labelRolename = new Label("role.name", role.getName());
                item.add(labelRolename);

                //
                Link linkDeleteRole = new Link("link.role.delete") {
                    @Override
                    public void onClick() {

                    }
                };
                linkDeleteRole.setVisible(!employee.hasRole(role));
                item.add(linkDeleteRole);
            }
        });
    }
}
