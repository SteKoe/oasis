package de.stekoe.idss;

import org.apache.log4j.Logger;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import de.stekoe.idss.model.User;

public class UserRolesAuthorizer implements IRoleCheckingStrategy {
    private static final Logger LOG = Logger
            .getLogger(UserRolesAuthorizer.class);

    public UserRolesAuthorizer() {
    }

    public boolean hasAnyRole(Roles roles) {
        IDSSSession authSession = (IDSSSession) Session.get();
        User user = authSession.getUser();

        if (user != null) {
            LOG.info(user.toString());
            return user.hasAnyRole(roles);
        }

        LOG.warn("No user found!");
        return false;
    }

}
