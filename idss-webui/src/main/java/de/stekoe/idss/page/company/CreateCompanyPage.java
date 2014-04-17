package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.model.CompanyRole;
import de.stekoe.idss.model.Employee;
import de.stekoe.idss.service.CompanyRoleService;
import de.stekoe.idss.session.WebSession;

public class CreateCompanyPage extends CompanyPage {

    @Inject
    private CompanyRoleService companyRoleService;

    public CreateCompanyPage(PageParameters params) {
        super(params);

        CompanyForm companyForm = new CompanyForm("form") {
            @Override
            protected void onSave(Company modelObject) {
                Company company = modelObject;

                CompanyRole companyRole = new CompanyRole();
                companyRole.setName("Administrator");
                company.getRoles().add(companyRole);

                Employee employee = new Employee();
                employee.setUser(WebSession.get().getUser());
                employee.setRole(companyRole);
                company.getEmployees().add(employee);

                companyRoleService.save(companyRole);
                companyService.save(company);
            }
        };
        add(companyForm);
    }
}
