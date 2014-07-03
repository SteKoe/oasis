package de.stekoe.idss.page;

import de.stekoe.idss.model.User;
import de.stekoe.idss.session.WebSession;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

/**
 * @author Stephan Koeninger 
 */
public class AuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {
    private static final Logger LOG = Logger.getLogger(AuthorizationStrategy.class);

    @Override
    public void onUnauthorizedInstantiation(Component component) {
        LOG.warn("Error 403");
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
        final User user = WebSession.get().getUser();

        AdminOnly adminRequired = (AdminOnly) c.getAnnotation(AdminOnly.class);
        UserOnly userRequired = (UserOnly) c.getAnnotation(UserOnly.class);

        if (adminRequired != null) {
            return (user != null && user.isAdmin());
        }

        if (userRequired != null) {
            return (user != null);
        }

        return true;
    }
}
