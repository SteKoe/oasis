package de.stekoe.idss.page;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.util.PasswordUtil;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;

import java.util.HashSet;
import java.util.Set;

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

        setPassword(new PasswordUtil().hashPassword(PASSWORD));
    }
}
