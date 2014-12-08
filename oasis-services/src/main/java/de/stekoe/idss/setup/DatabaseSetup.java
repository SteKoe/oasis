package de.stekoe.idss.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class DatabaseSetup {

    private static final Logger LOG = Logger.getLogger(DatabaseSetup.class);

    @Inject
    UserService userService;

    @Inject
    SystemRoleService systemRoleService;

    @Inject
    ProjectService projectService;

    @Inject
    AuthService authService;


    /*
     * Ärztlicher Dienst
     * Pflegedienst
     * IT
     * Medizin Controlling
     */
    private static final String aerztlicherDienst = "Ärztlicher Dienst";
    private static final String pflegeDienst = "Pflegedienst";
    private static final String it = "IT";
    private static final String medizinControlling = "Medizin Controlling";

    private static final List<String> SYSTEMROLES = Arrays.asList(
            SystemRole.USER,
            SystemRole.ADMIN
    );

    private void createSystemRoles() {
        if(systemRoleService.findAll().isEmpty()) {
            for (String systemRole : SYSTEMROLES) {
                SystemRole role = new SystemRole();
                role.setName(systemRole);
                systemRoleService.save(role);
            }
        }
    }


    private void createUsers() {
        try {
            // Admin
            User user = createUser("administrator", "");
            List<SystemRole> systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getAdminRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Michael
            user = createUser("Michael Hess", "");
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Stephan
            user = createUser("Stephan Köninger", "");
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Dr. Müller
            user = createUser("Dr. Müller", aerztlicherDienst);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Dr. Meier
            user = createUser("Dr. Meier", aerztlicherDienst);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Sr. Silke
            user = createUser("Sr. Silke", pflegeDienst);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Pfl. Peter
            user = createUser("Pfl. Peter", pflegeDienst);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Katharina Klick
            user = createUser("Katharina Klick", it);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getAdminRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Manfred Maus
            user = createUser("Manfred Maus", it);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getAdminRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Petra Prüfer
            user = createUser("Petra Prüfer", medizinControlling);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);

            // Bernd Berger
            user = createUser("Bernd Berger", medizinControlling);
            systemRoles = new ArrayList<SystemRole>();
            systemRoles.add(systemRoleService.getUserRole());
            user.setRoles(new HashSet<SystemRole>(systemRoles));
            userService.save(user);
        } catch(Exception e) {
            LOG.error("Error while saving User!", e);
        }
    }


    private User createUser(String name, String professional) {
        String username = name.toLowerCase().replace(".","").replace(" ",".");
        String email = name.toLowerCase().replace(".", "").replace(" ", ".") + "@example.com";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(authService.hashPassword("password"));
        user.setActivationKey(null);
        user.setUserStatus(UserStatus.ACTIVATED);
        return user;
    }

    private void installSampleUser() {
        createSystemRoles();
        createUsers();
    }

    public void run() {
        if(userService.findAll().size() == 0) {
            installSampleUser();
        }
    }
}
