package de.stekoe.idss.page.auth.user;

import org.apache.wicket.RestartResponseAtInterceptPageException;

import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LayoutPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {

    /**
     * Construct.
     */
    public LogoutPage() {
        getSession().invalidate();
        info("Sie haben sich erfolgreich abgemeldet!");
        throw new RestartResponseAtInterceptPageException(HomePage.class);
    }
}
