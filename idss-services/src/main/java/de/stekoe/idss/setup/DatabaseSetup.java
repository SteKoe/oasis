package de.stekoe.idss.setup;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.*;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    ProjectService projectService;

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

    private void createSystemRoles() {
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

    private void createUsers() {
        for(java.lang.String name : USERNAMES) {
            User user = new User();
            user.setEmail(name.toLowerCase() + "@example.com");
            user.setUsername(name.toLowerCase());
            user.setPassword(authService.hashPassword("password"));
            user.setActivationKey(null);
            user.setUserStatus(UserStatus.ACTIVATED);

            if(user.getUsername().contains("admin")) {
                user.setPassword("admin");
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

    private void createSampleProject() {
        Project project = new Project();

        final ProjectRole projectRoleForCreator = createProjectRoleForCreator(project);
        final ProjectRole projectRoleForMember = createProjectRoleForMember(project);

        project.setName("Sample Name for Project");
        project.setDescription("Contrary to popular belief, Lorem Ipsum is not simply random text. <strong>It has roots</strong> in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance.");
        project.getProjectRoles().add(projectRoleForCreator);
        project.getProjectRoles().add(projectRoleForMember);

        ProjectMember projectCreator = new ProjectMember();
        projectCreator.setUser(userService.findByUsername("klara.fall"));
        projectCreator.setProjectRole(projectRoleForCreator);

        project.getProjectTeam().add(projectCreator);

        projectService.save(project);
    }

    private ProjectRole createProjectRoleForCreator(Project project) {
        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");

        final Set<Permission> projectPermissions = new HashSet<Permission>();
        for (PermissionType permissionType : PermissionType.forProject()) {
            projectPermissions.add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }

        projectRoleCreator.setPermissions(projectPermissions);
        return projectRoleCreator;
    }

    private ProjectRole createProjectRoleForMember(Project project) {
        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");

        for (PermissionType permissionType : PermissionType.forReadOnly()) {
            projectRoleMember.getPermissions().add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }
        return projectRoleMember;
    }

    public void installSampleUser() {
        createSystemRoles();
        createUsers();
    }

    public void installSampleProject() {
        createSampleProject();
    }
}
