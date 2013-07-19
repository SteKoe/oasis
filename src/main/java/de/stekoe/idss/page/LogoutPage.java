package de.stekoe.idss.page;

import org.apache.wicket.RestartResponseAtInterceptPageException;

import de.stekoe.idss.IDSSSession;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {

    /**
     * Construct.
     */
    public LogoutPage() {
        IDSSSession.get().invalidate();
        info("Sie haben sich erfolgreich abgemeldet!");
        throw new RestartResponseAtInterceptPageException(HomePage.class);
    }
}
