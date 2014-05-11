package de.stekoe.idss;


import java.util.HashSet;
import java.util.Set;

import org.mindrot.jbcrypt.BCrypt;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;

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
        userRole.setName(SystemRole.USER);
        systemroles.add(userRole);

        user.setRoles(systemroles);

        return user;
    }

    public static User createAuthAdminUser() {
        User user = new User();

        user.setEmail("admin@example.com");
        user.setUsername(ADMIN_USERNAME);
        user.setPassword(BCrypt.hashpw(ADMIN_PASSWORD, BCrypt.gensalt()));

        Set<SystemRole> systemroles = new HashSet<SystemRole>();

        SystemRole adminRole = new SystemRole();
        adminRole.setName(SystemRole.ADMIN);
        systemroles.add(adminRole);

        user.setRoles(systemroles);

        return user;
    }

    public static Project createProjectWithTeam(int teamSize) {
        Project project = new Project();
        project.setName("Testproject");
        for(int i = 0; i < teamSize; i++) {
            ProjectMember pm = createProjectMember();
            pm.getUser().setUsername("User " + i);
            project.getProjectTeam().add(pm);
        }
        return project;
    }

    private static ProjectMember createProjectMember() {
        ProjectMember pm = new ProjectMember();
        pm.setUser(new User());

        return pm;
    }
}
