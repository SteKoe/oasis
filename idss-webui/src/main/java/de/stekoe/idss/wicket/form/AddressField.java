package de.stekoe.idss.wicket.form;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.Address;
import de.stekoe.idss.page.component.behavior.Placeholder;

public class AddressField extends FormComponentPanel<Address> {
    private static final long serialVersionUID = 201404141112L;
    private final TextField<String> streetField;
    private final TextField<String> zipField;
    private final TextField<String> cityField;
    private final TextField<String> countryField;
    private final TextField<String> numberField;

    public AddressField(final String wicketId, IModel<Address> model) {
        super(wicketId, new CompoundPropertyModel<Address>(model));

        FormGroup streetGroup = new FormGroup("group.street");
        streetField = new TextField<String>("street");
        streetField.add(new Placeholder(getString("label.street")));
        streetGroup.add(streetField);

        numberField = new TextField<String>("number");
        numberField.add(new Placeholder(getString("label.number")));
        streetGroup.add(numberField);
        add(streetGroup);

        FormGroup cityGroup = new FormGroup("group.city");
        zipField = new TextField<String>("zip");
        zipField.add(new Placeholder(getString("label.zip")));
        cityGroup.add(zipField);

        cityField = new TextField<String>("city", new PropertyModel<String>(model, "city"));
        cityField.add(new Placeholder(getString("label.city")));
        cityGroup.add(cityField);
        add(cityGroup);

        FormGroup countryGroup = new FormGroup("group.country");
        countryField = new TextField<String>("country", new PropertyModel<String>(model, "country"));
        countryField.add(new Placeholder(getString("label.country")));
        countryGroup.add(countryField);
        add(countryGroup);
    }

    @Override
    protected void convertInput() {
        Address address = new Address();
        address.setStreet(streetField.getValue());
        address.setNumber(numberField.getValue());
        address.setZip(zipField.getValue());
        address.setCity(cityField.getValue());
        address.setCountry(countryField.getValue());
        if(address.hasValues()) {
            setConvertedInput(address);
        } else {
            setConvertedInput(null);
        }
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();

        Address address = this.getModelObject();
        if(address != null) {
            streetField.setModelObject(address.getStreet());
            numberField.setModelObject(address.getNumber());
            zipField.setModelObject(address.getZip());
            cityField.setModelObject(address.getCity());
            countryField.setModelObject(address.getCountry());
        }
    }

}
