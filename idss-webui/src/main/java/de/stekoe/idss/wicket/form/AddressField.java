package de.stekoe.idss.wicket.form;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.Address;
import de.stekoe.idss.page.component.behavior.Placeholder;

public class AddressField extends FormComponentPanel<Address> {
    private static final long serialVersionUID = 201404141112L;

    public AddressField(final String wicketId, IModel<Address> model) {
        super(wicketId);

        FormGroup streetGroup = new FormGroup("group.street");
        TextField<String> streetField = new TextField<String>("street", new PropertyModel<String>(model, "street"));
        streetField.add(new Placeholder(getString("label.street")));
        streetGroup.add(streetField);
        add(streetGroup);

        FormGroup cityGroup = new FormGroup("group.city");
        TextField<String> zipField = new TextField<String>("zip", new PropertyModel<String>(model, "zip"));
        zipField.add(new Placeholder(getString("label.zip")));
        cityGroup.add(zipField);

        TextField<String> cityField = new TextField<String>("city", new PropertyModel<String>(model, "city"));
        cityField.add(new Placeholder(getString("label.city")));
        cityGroup.add(cityField);
        add(cityGroup);

        FormGroup countryGroup = new FormGroup("group.country");
        TextField<String> countryField = new TextField<String>("country", new PropertyModel<String>(model, "country"));
        countryField.add(new Placeholder(getString("label.country")));
        countryGroup.add(countryField);
        add(countryGroup);
    }

}
