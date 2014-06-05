/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.session;

import org.apache.log4j.Logger;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.service.UserService;

/**
 * @author Stephan Koeninger 
 */
@SuppressWarnings("serial")
public class WebSession extends AuthenticatedWebSession {

    @SpringBean
    UserService userService;
    @SpringBean
    AuthService authService;

    private User user;
    private static final Logger LOG = Logger.getLogger(WebSession.class);

    /**
     * @param request The current request.
     */
    public WebSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    /**
     * @return The User saved in the session.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * @param user User to be stored in session.
     */
    void setUser(User user) {
        this.user = user;
    }

    /**
     * @return The current session instance.
     */
    public static WebSession get() {
        return (WebSession) org.apache.wicket.Session.get();
    }

    @Override
    public Roles getRoles() {
        LOG.info("Checking roles on user " + getUser());
        if (getUser() != null) {
            return new Roles(getUser().getRoles().toArray(new java.lang.String[getUser().getRoles().size()]));
        }

        return new Roles();
    }

    /**
     * This method is called by {@link #signIn(java.lang.String, java.lang.String)}
     *
     * @param username The username to authenticate
     * @param password The password of the user to authenticate
     * @return true if username and password match, false otherwise
     */
    @Override
    public boolean authenticate(java.lang.String username, java.lang.String password) {
        final AuthStatus authStatus = authenticateByService(username, password);

        if (authStatus.equals(AuthStatus.SUCCESS)) {
            final User user = getUserFromService(username);
            setUser(user);
            return true;
        }

        return false;
    }

    @Override
    public void signOut() {
        super.signOut();
        setUser(null);
    }

    @Override
    public void invalidate() {
        signOut();
        super.invalidate();
    }

    /*
     * Since this object is instanciated manually, we have to get the beans for user service manually, too.
     */
    protected User getUserFromService(java.lang.String username) {
        return userService.findByUsernameOrEmail(username);
    }

    protected AuthStatus authenticateByService(java.lang.String username, java.lang.String password) {
         return authService.authenticate(username, password);
    }

}
