package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.Company;
import de.stekoe.idss.wicket.form.AddressField;
import de.stekoe.idss.wicket.model.CompanyModel;

public class CompanyForm extends Panel {

    @Inject
    CompanyModel companyModel;

    public CompanyForm(final String wicketId) {
        this(wicketId, null);
    }

    public CompanyForm(String wicketId, String companyId) {
        super(wicketId);

        if(!StringUtils.isBlank(companyId)) {
            companyModel.setId(companyId);
        }

        Form<Company> form = new Form<Company>("form.company", companyModel);
        add(form);

        form.add(new TextField<String>("name"));
        form.add(new AddressField("address"));
    }

}
