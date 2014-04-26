package de.stekoe.idss.page.company;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Address;
import de.stekoe.idss.page.component.AddressPanel;

public class CompanyDetailsPage extends CompanyPage {
    public CompanyDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new Label("company.name", new PropertyModel<String>(getCompany(), "name")));

        ListView<Address> addresses = new ListView<Address>("company.addresses", getCompany().getAddresses()) {
            @Override
            protected void populateItem(ListItem<Address> item) {
                item.add(new AddressPanel("address", item.getModelObject()));
            }
        };
        addresses.setVisible(false);
        add(addresses);
    }
}
