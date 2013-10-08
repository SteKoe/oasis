package de.stekoe.idss.page;

import java.util.HashSet;
import java.util.Set;

import de.stekoe.idss.model.SystemRole;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.mindrot.jbcrypt.BCrypt;

import de.stekoe.idss.model.User;

/**
 * This class provides a very basic user object which has access
 * to pages restricted to administrators.
 *
 * Username is "root"
 * Password is "root"
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class AdminAuthUser extends User {

    /** Password exposed due to testing purposes */
    public static final String PASSWORD = "root";

    public AdminAuthUser() {
        setUsername("root");

        Set<SystemRole> systemroles = new HashSet<SystemRole>();

        SystemRole userRole = new SystemRole();
        userRole.setName(Roles.USER);
        systemroles.add(userRole);

        SystemRole adminRole = new SystemRole();
        adminRole.setName(Roles.ADMIN);
        systemroles.add(adminRole);

        setRoles(systemroles);

        setPassword(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));
    }
}
