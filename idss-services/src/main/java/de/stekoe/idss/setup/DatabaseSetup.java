package de.stekoe.idss.setup;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DatabaseSetup {

    private static final Logger LOG = Logger.getLogger(DatabaseSetup.class);

    @Inject
    UserService userService;

    @Inject
    SystemRoleService systemRoleService;

    @Inject
    ProjectRoleService projectRoleService;

    @Inject
    AuthService authService;

    private static final List<java.lang.String> SYSTEMROLES = Arrays.asList(
            SystemRole.USER,
            SystemRole.ADMIN
    );

    private static final List<java.lang.String> USERNAMES = Arrays.asList(
            "administrator",
            "rainer.zufall",
            "klara.fall",
            "anna.gramm",
            "anna.nass",
            "hans.wurst",
            "jeff.trainer",
            "jo.kher",
            "kai.ser",
            "peer.manent",
            "melita.kaffee",
            "jonas.sprenger",
            "benedikt.ritter",
            "michael.hess",
            "stephan.koeninger"
    );

    public void createSystemRoles() {
        for(java.lang.String systemRole : SYSTEMROLES) {
            SystemRole role = new SystemRole();
            role.setName(systemRole);

            try {
                systemRoleService.save(role);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }

    public void createUsers() {
        for(java.lang.String name : USERNAMES) {
            User user = new User();
            user.setEmail(name.toLowerCase() + "@example.com");
            user.setUsername(name.toLowerCase());
            user.setPassword(authService.hashPassword("password"));
            user.setActivationKey(null);
            user.setUserStatus(UserStatus.ACTIVATED);

            if(user.getUsername().contains("admin")) {
                final List<SystemRole> systemRoles = Arrays.asList(systemRoleService.getAdminRole());
                user.setRoles(new HashSet<SystemRole>(systemRoles));
            } else {
                final List<SystemRole> systemRoles = Arrays.asList(systemRoleService.getUserRole());
                user.setRoles(new HashSet<SystemRole>(systemRoles));
            }

            try {
                userService.save(user);
            } catch (Exception e) {
                LOG.error(e);
            }
        }
    }
}
