package de.stekoe.idss.page;

import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.mindrot.jbcrypt.BCrypt;

import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;

/**
 * This class provides a very basic user object which has access
 * to pages restricted to registered users.
 *
 * Username is "user"
 * Password is "user"
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserAuthUser extends User {

    /** Password exposed due to testing purposes */
    public static final String PASSWORD = "user";
    private static final String EMAIL = "mail@example.com";

    public UserAuthUser() {
        setUsername(PASSWORD);
        setEmail(EMAIL);

        Set<Role> systemroles = new HashSet<Role>();

        Role userRole = new Role();
        userRole.setRoleName(Roles.USER);
        systemroles.add(userRole);

        setRoles(systemroles);

        setPassword(BCrypt.hashpw(PASSWORD, BCrypt.gensalt()));
    }
}
