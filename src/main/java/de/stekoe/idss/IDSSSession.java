package de.stekoe.idss;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class IDSSSession extends WebSession {

    private User user;

    /**
     * @param request The current request.
     */
    public IDSSSession(Request request) {
        super(request);
    }

    /**
     * @return The User saved in the session.
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user User to be stored in session.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return true if a user is logged in, false if not.
     */
    public boolean isLoggedIn() {
        return getUser() != null;
    }

    /**
     * @return The current IDSSSession instance.
     */
    public static IDSSSession get() {
        return (IDSSSession) Session.get();
    }
}
