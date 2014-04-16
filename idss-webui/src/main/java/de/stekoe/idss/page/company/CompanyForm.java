package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.WebApplication;
import de.stekoe.idss.model.Address;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.CompanyService;

public class CompanyForm extends Panel {

    @Inject
    CompanyService companyService;

    CompanyModel companyModel;

    public CompanyForm(final String wicketId) {
        this(wicketId, null);
    }

    public CompanyForm(String wicketId, String companyId) {
        super(wicketId);

        if(!StringUtils.isBlank(companyId)) {
            companyModel = new CompanyModel(companyId);
        } else {
            companyModel = new CompanyModel();
        }

        Form<Company> form = new Form<Company>("form.company", companyModel) {
            @Override
            protected void onSubmit() {
                companyService.save(getModelObject());
                success("Save done.");
                setResponsePage(WebApplication.get().getHomePage());
            }
        };
        add(form);

        FormGroup nameGroup = new FormGroup("group.name");
        TextField<String> nameField = new TextField<String>("name", new PropertyModel<String>(companyModel, "name"));
        nameField.add(new Placeholder(getString("label.name")));
        nameGroup.add(nameField);
        form.add(nameGroup);
    }

    public class CompanyModel extends Model<Company> {
        private String id;
        private Company company;

        public CompanyModel() {
            this.id = null;
        }

        public CompanyModel(String id) {
            this.id = id;
        }

        @Override
        public Company getObject() {
            if(this.company != null) {
                return this.company;
            }

            if(this.id != null) {
                return companyService.findOne(this.id);
            } else {
                Company company = new Company();
                company.getAddresses().add(new Address());
                this.company = company;
                return company;
            }
        }

        @Override
        public void detach() {
            this.company = null;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
