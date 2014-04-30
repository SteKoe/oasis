package de.stekoe.idss.page.company;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.component.user.UserInfoBlock;
import de.stekoe.idss.service.CompanyService;

public class EmployeeListPage extends CompanyPage {

    @SpringBean
    private CompanyService companyService;

    public EmployeeListPage(PageParameters pageParameters) {
        super(pageParameters);

        Button linkAddEmployee = new Button("link.add");
        linkAddEmployee.add(new ButtonBehavior(Type.Success));
        add(linkAddEmployee);

        add(new ListView<Employee>("employees", getCompany().getEmployees()) {
            @Override
            protected void populateItem(ListItem<Employee> item) {
                Employee employee = item.getModelObject();

                User user = employee.getUser();

                String roleName = "";
                if(employee.getRole() != null) {
                    roleName = employee.getRole().getName();
                }
                item.add(new UserInfoBlock("employee", user, roleName));
            }
        });

        AddEmployeeModal modalAddEmployee = new AddEmployeeModal("modal.employee.add") {
            @Override
            public void onSave(User existingUser) {
                if(existingUser != null) {
                    Employee employee = new Employee();
                    employee.setUser(existingUser);

                    Company company = getCompany();
                    company.getEmployees().add(employee);
                    companyService.save(company);

                    success(existingUser.toString());

                } else {
                    error("User not found!");
                }
            }
        };
        add(modalAddEmployee);
        modalAddEmployee.addOpenerAttributesTo(linkAddEmployee);
    }
}
