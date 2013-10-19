package de.stekoe.idss.page.auth;

import org.apache.log4j.Logger;

import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LayoutPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(LogoutPage.class);

    /**
     * Construct.
     */
    public LogoutPage() {
        if(getSession().getUser() != null) {
            LOG.info("User " + getSession().getUser().getUsername() + " is about to log out!");
        }

        getSession().invalidate();
        getSession().success(getString("message.logout.success"));
        setResponsePage(HomePage.class);
    }
}
