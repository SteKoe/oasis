package de.stekoe.idss.page.admin;

import de.stekoe.idss.WebApplication;
import de.stekoe.idss.page.AuthAdminPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SystemInformationPage extends AuthAdminPage {
    public SystemInformationPage() {
        super();

        List<String> paramters = new ArrayList<String>();
        final ServletContext servletContext = WebApplication.get().getServletContext();
        final Enumeration initParameterNames = servletContext.getInitParameterNames();
        while(initParameterNames.hasMoreElements()) {
            final String key = initParameterNames.nextElement().toString();
            paramters.add(key);
        }

        final ListView<String> paramsList = new ListView<String>("params.list", paramters) {
            @Override
            protected void populateItem(ListItem<String> item) {
                final String key = item.getModelObject();
                item.add(new Label("key", key));
                item.add(new Label("value", WebApplication.get().getServletContext().getInitParameter(key)));
            }
        };
        add(paramsList);
    }
}
