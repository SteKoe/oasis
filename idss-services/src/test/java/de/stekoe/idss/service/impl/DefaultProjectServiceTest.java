package de.stekoe.idss.service.impl;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
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
    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectRoleService projectRoleService;
    @Autowired
    UserService userService;

    @Test
    public void projectRoleBasedAuthWorks() throws Exception {
        java.lang.String projectId = "P2345";

        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, projectId));
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));

        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("LEADER");
        projectRole.setPermissions(permissions);
        projectRoleService.save(projectRole);

        final User user = TestFactory.createRandomUser();
        userService.save(user);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProjectRole(projectRole);

        final Project project = TestFactory.createProject();
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
        final User user = TestFactory.createUser("Projektleiter User");
        userService.save(user);

        Project project = TestFactory.createProject();

        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");
        for (PermissionType permissionType : PermissionType.forProject()) {
            projectRoleCreator.getPermissions().add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }
        projectRoleService.save(projectRoleCreator);

        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");
        for (PermissionType permissionType : PermissionType.forReadOnly()) {
            projectRoleMember.getPermissions().add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }
        projectRoleService.save(projectRoleMember);

        project.getProjectRoles().add(projectRoleCreator);
        project.getProjectRoles().add(projectRoleMember);

        ProjectMember projectCreator = new ProjectMember();
        projectCreator.setUser(userService.findById(user.getId()));
        projectCreator.setProjectRole(projectRoleCreator);

        project.getProjectTeam().add(projectCreator);
        projectService.save(project);
    }
}
