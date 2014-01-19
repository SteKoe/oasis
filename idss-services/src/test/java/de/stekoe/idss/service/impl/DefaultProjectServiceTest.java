package de.stekoe.idss.service.impl;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.*;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultProjectServiceTest extends AbstractBaseTest {
    @Autowired ProjectService projectService;
    @Autowired UserService userService;

    @Test
    public void projectRoleBasedAuthWorks() throws Exception {
        java.lang.String projectId = "P2345";

        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, projectId));
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));

        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("LEADER");
        projectRole.setPermissions(permissions);

        Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
        projectRoles.add(projectRole);

        final User user = new User();
        userService.save(user);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProjectRoles(projectRoles);

        final Project project = new Project();
        project.setId(projectId);
        project.getProjectTeam().add(pm);

        projectService.save(project);

        assertTrue(projectService.isAuthorized(user.getId(), project.getId(), PermissionType.DELETE));
        assertTrue(projectService.isAuthorized(user.getId(), project.getId(), PermissionType.UPDATE));
        assertFalse(projectService.isAuthorized(user.getId(), project.getId(), PermissionType.READ));
        assertFalse(projectService.isAuthorized(user.getId(), project.getId(), PermissionType.CREATE));
    }

    @Test
    public void saving() throws Exception {
        Project project = new Project();

        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");
        projectRoleCreator.setPermissions(Permission.createAll(PermissionObject.PROJECT, project.getId()));

        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");
        projectRoleMember.setPermissions(Permission.createReadOnly(PermissionObject.PROJECT, project.getId()));

        project.getProjectRoles().add(projectRoleCreator);
        project.getProjectRoles().add(projectRoleMember);

        ProjectMember projectCreator = new ProjectMember();
        projectCreator.setUser(new User());
        projectCreator.getProjectRoles().add(projectRoleCreator);

        project.getProjectTeam().add(projectCreator);
        projectService.save(project);
    }
}
