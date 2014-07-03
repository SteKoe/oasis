package de.stekoe.idss.page;

import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * @author Stephan Koeninger
 */
@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(LogoutPage.class);

    /**
     * Construct.
     */
    public LogoutPage() {
        if (getSession().getUser() != null) {
            LOG.info("User " + getSession().getUser().getUsername() + " is about to log out!");
        }

        Locale locale = getSession().getLocale();
        getSession().invalidate();
        getSession().success(getString("message.logout.success"));
        getSession().setLocale(locale);
        setResponsePage(HomePage.class);
    }
}
