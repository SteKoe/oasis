package de.stekoe.idss.page.company;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Employee;
import de.stekoe.idss.page.component.user.UserInfoBlock;

public class EmployeeListPage extends CompanyPage {
    public EmployeeListPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new ListView<Employee>("employees", getCompany().getEmployees()) {
            @Override
            protected void populateItem(ListItem<Employee> item) {
                Employee employee = item.getModelObject();

                item.add(new UserInfoBlock("employee", employee.getUser(), employee.getRole().getName()));
            }
        });
    }
}
