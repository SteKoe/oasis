package de.stekoe.idss;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService.LoginStatus;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class IDSSSession extends AuthenticatedWebSession {

    private static final Logger LOG = Logger.getLogger(IDSSSession.class);

    private User user;
    private LoginStatus loginStatus;

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
     * @param loginStatus The current loginStatus
     */
    private void setLoginStatus(LoginStatus loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * @return the last known login status.
     */
    public LoginStatus getLoginStatus() {
        return this.loginStatus;
    }

    /**
     * @return The current IDSSSession instance.
     */
    public static IDSSSession get() {
        return (IDSSSession) Session.get();
    }

    @Override
    public Roles getRoles() {
        LOG.info("Checking roles on user " + getUser());
        if(getUser() != null) {
            return new Roles(getUser().getRoles().toArray(new String[getUser().getRoles().size()]));
        }

        return new Roles();
    }

    @Override
    public boolean authenticate(String username, String password) {
        setLoginStatus(null);

        LoginStatus loginStatus = IDSSServices.getUserService().login(username, password);
        if (loginStatus.equals(LoginStatus.SUCCESS)) {
            return true;
        } else {
            setLoginStatus(loginStatus);
            return false;
        }
    }
}
