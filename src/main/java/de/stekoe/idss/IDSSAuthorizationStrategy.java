package de.stekoe.idss;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

import de.stekoe.idss.annotation.auth.AdminOnly;
import de.stekoe.idss.annotation.auth.UserOnly;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.LoginPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class IDSSAuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {
    private static final Logger LOG = Logger.getLogger(IDSSAuthorizationStrategy.class);

    @Override
    public void onUnauthorizedInstantiation(Component component) {
        LOG.error("ERROR 403: Redirecting user!");
        throw new RestartResponseAtInterceptPageException(LoginPage.class);
    }

    @Override
    public boolean isInstantiationAuthorized(Class componentClass) {
        return isAuthorized(componentClass);
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        if (action.equals(Component.RENDER)) {
            return isAuthorized(component.getClass());
        }
        return true;
    }

    private boolean isAuthorized(Class c) {
        AdminOnly adminRequired = (AdminOnly) c.getAnnotation(AdminOnly.class);
        UserOnly userRequired = (UserOnly) c.getAnnotation(UserOnly.class);

        if (adminRequired != null) {
            User user = IDSSSession.get().getUser();
            return (user != null && user.isAdmin());
        }

        if (userRequired != null) {
            User user = IDSSSession.get().getUser();
            return (user != null);
        }

        return true;
    }
}