package de.stekoe.idss.page.component;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.Address;

public class AddressPanel extends Panel {

    public AddressPanel(String id, Address address) {
        super(id);

        Label labelStreet = new Label("street", address.getStreet() + " " + address.getNumber());
        add(labelStreet);

        Label labelCity = new Label("city", address.getZip() + " " + address.getCity());
        add(labelCity);

        Label labelCountry = new Label("country", address.getCountry());
        add(labelCountry);
    }

}
