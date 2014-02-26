package de.stekoe.idss;


import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

public class TestFactory {

    public static final String USER_USERNAME = "normalUser";
    public static final String USER_PASSWORD = "password";

    public static final String ADMIN_USERNAME = "root";
    public static final String ADMIN_PASSWORD = "root";

    public static User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(username.toLowerCase().replace(" ", "") + "@example.com");
        user.setPassword(password);
        return user;
    }

    public static User createUser(java.lang.String username) {
        return createUser(username, "secret");
    }

    public static User createAuthUser() {
        User user = new User();

        user.setUsername(USER_USERNAME);
        user.setEmail("mail@example.com");
        user.setPassword(BCrypt.hashpw(USER_PASSWORD, BCrypt.gensalt()));

        Set<SystemRole> systemroles = new HashSet<SystemRole>();

        SystemRole userRole = new SystemRole();
        userRole.setName(Roles.USER);
        systemroles.add(userRole);

        user.setRoles(systemroles);

        return user;
    }

    public static User createAuthAdminUser() {
        User user = new User();

        user.setUsername(ADMIN_USERNAME);
        user.setPassword(BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt()));

        Set<SystemRole> systemroles = new HashSet<SystemRole>();

        SystemRole userRole = new SystemRole();
        userRole.setName(Roles.USER);
        systemroles.add(userRole);

        SystemRole adminRole = new SystemRole();
        adminRole.setName(Roles.ADMIN);
        systemroles.add(adminRole);

        user.setRoles(systemroles);

        return user;
    }
}
