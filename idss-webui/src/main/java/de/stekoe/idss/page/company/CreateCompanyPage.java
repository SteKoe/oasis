package de.stekoe.idss.page.company;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.page.AuthUserPage;

public class CreateCompanyPage extends AuthUserPage {
    public CreateCompanyPage(PageParameters params) {
        add(new CompanyForm("form"));
    }
}
