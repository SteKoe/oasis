package de.stekoe.idss.wicket.form;

import org.apache.wicket.markup.html.form.FormComponentPanel;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.Address;
import de.stekoe.idss.page.component.behavior.Placeholder;

public class AddressField extends FormComponentPanel<Address> {
    private static final long serialVersionUID = 201404141112L;

    public AddressField(final String wicketId) {
        this(wicketId, null);
    }

    public AddressField(final String wicketId, IModel<Address> model) {
        super(wicketId, model);

        TextField<String> streetField = new TextField<String>("street");
        streetField.add(new Placeholder(getString("label.street")));
        add(streetField);

        TextField<String> zipField = new TextField<String>("zip");
        zipField.add(new Placeholder(getString("label.zip")));
        add(zipField);

        TextField<String> cityField = new TextField<String>("city");
        cityField.add(new Placeholder(getString("label.city")));
        add(cityField);

        TextField<String> countryField = new TextField<String>("country");
        countryField.add(new Placeholder(getString("label.country")));
        add(countryField);
    }

}
