package de.stekoe.idss.page.company;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.OASISWebApplication;

public class EditCompanyPage extends CompanyPage {
    public EditCompanyPage(PageParameters params) {
        super(params);

        StringValue companyIdValue = params.get("companyId");
        if(companyIdValue.isEmpty()) {
            setResponsePage(OASISWebApplication.get().getHomePage());
        }

        add(new CompanyForm("form", companyIdValue.toString()));
    }
}
