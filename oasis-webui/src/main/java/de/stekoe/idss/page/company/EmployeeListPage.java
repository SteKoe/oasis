package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.commons.validator.GenericValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons.Type;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.component.user.UserInfoBlock;
import de.stekoe.idss.service.CompanyService;
import de.stekoe.idss.service.MailService;

public class EmployeeListPage extends CompanyPage {

    @Inject
    private MailService mailService;

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

        AddOrInviteUserModal modalAddEmployee = new AddOrInviteUserModal("modal.employee.add") {
            @Override
            protected void onInitialize() {
                super.onInitialize();
                header(Model.of(getString("label.project.add.member")));
            }

            @Override
            public void foundExistingUser(User user) {
                Employee employee = new Employee();
                employee.setUser(user);

                Company company = getCompany();
                company.getEmployees().add(employee);
                companyService.save(company);

                success(getString("message.save.success"));
            }

            @Override
            public void inviteUser(String usernameOrEmail) {
                if(checkIfIsEmail(usernameOrEmail)) {
                    success(String.format(getString("message.invitation.sent"), usernameOrEmail));
                    mailService.sendMail(usernameOrEmail, "Invitation", "Body");
                } else {
                    error(getString("message.user.notfound"));
                }
            }

            private boolean checkIfIsEmail(String usernameOrEmail) {
                return GenericValidator.isEmail(usernameOrEmail);
            }
        };
        add(modalAddEmployee);
        modalAddEmployee.addOpenerAttributesTo(linkAddEmployee);
    }
}
