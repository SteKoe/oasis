package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.Address;
import de.stekoe.idss.model.Company;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.AddressService;
import de.stekoe.idss.wicket.form.AddressField;

public class CompanyAddressForm extends Panel {

    @Inject
    AddressService addressService;

    CompanyAddressModel companyModel;

    public CompanyAddressForm(final String wicketId) {
        this(wicketId, null);
    }

    public CompanyAddressForm(String wicketId, String companyId) {
        super(wicketId);

        if(!StringUtils.isBlank(companyId)) {
            companyModel = new CompanyAddressModel(companyId);
        } else {
            companyModel = new CompanyAddressModel();
        }

        Form<Address> form = new Form<Address>("form.company", companyModel) {
            @Override
            protected void onSubmit() {
                addressService.save(getModelObject());
                success("Save done.");
                setResponsePage(EditCompanyPage.class, new PageParameters().add("companyId", getModelObject().getId()));
            }
        };
        add(form);

        FormGroup nameGroup = new FormGroup("group.name");
        TextField<String> nameField = new TextField<String>("name", new PropertyModel<String>(companyModel, "name"));
        nameField.add(new Placeholder(getString("label.name")));
        nameGroup.add(nameField);
        form.add(nameGroup);

        AddressField addressField = new AddressField("addresses.0", new PropertyModel<Address>(companyModel, "addresses.0"));
        form.add(addressField);
    }

    /**
     * @param modelObject
     */
    protected void onSave(Company modelObject) {
    }

    public class CompanyAddressModel extends Model<Address> {
        private String id;
        private Address address;

        public CompanyAddressModel() {
            this.id = null;
        }

        public CompanyAddressModel(String id) {
            this.id = id;
        }

        @Override
        public Address getObject() {
            if(this.address != null) {
                return this.address;
            }

            if(this.id != null) {
                Address address = addressService.findOne(this.id);
                return address;
            } else {
                this.address = new Address();
                return this.address;
            }
        }

        @Override
        public void detach() {
            this.address = null;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
