package de.stekoe.idss.page;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.setup.DatabaseSetup;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class InjectCheckPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(InjectCheckPage.class);

    @SpringBean
    IUserDAO userDao;

    @SpringBean
    IProjectDAO projectDAO;

    @SpringBean
    private DatabaseSetup databaseSetup;

    /**
     * Construct.
     */
    public InjectCheckPage() {
        this(null);
    }

    /**
     * Construct.
     *
     * @param parameters Wrapped page parameters
     */
    public InjectCheckPage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        RepeatingView view = new RepeatingView("injectCheckList");
        add(view);

        view.add(new Label(view.newChildId(), isInjected("IUserDAO " + userDao)));
        view.add(new Label(view.newChildId(), isInjected("IProjectDAO " + projectDAO)));

        databaseSetup.createSystemRoles();
        databaseSetup.createUsers();
    }

    private String isInjected(Object obj) {
        final String injectionStatus = ((obj != null) ? "true" : "false");
        return injectionStatus;
    }
}
