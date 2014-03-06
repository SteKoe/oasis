/*
 * Copyright 2014 Stephan Köninger
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

package de.stekoe.idss.page.auth;

import de.stekoe.idss.model.User;
import de.stekoe.idss.page.auth.annotation.AdminOnly;
import de.stekoe.idss.page.auth.annotation.UserOnly;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;

/**
 * @author Stephan Köninger <mail@stekoe.de>
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
