package de.stekoe.idss.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.User;

public class ProjectServiceTest extends AbstractBaseTest {
    @Inject
    ProjectService projectService;

    @Inject
    ProjectRoleService projectRoleService;

    @Inject
    UserService userService;

    @Test
    public void projectRoleBasedAuthWorks() throws Exception {
        final Project project = TestFactory.createProject();

        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, project.getId()));
        permissions.add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, project.getId()));

        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("LEADER");
        projectRole.setPermissions(permissions);
        projectRoleService.save(projectRole);

        final User user = TestFactory.createRandomUser();
        userService.save(user);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        pm.setProjectRole(projectRole);

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
        projectCreator.setUser(userService.findOne(user.getId()));
        projectCreator.setProjectRole(projectRoleCreator);

        project.getProjectTeam().add(projectCreator);
        projectService.save(project);
    }

    @Test
    public void findProjectsByUser() throws Exception {
        User user = TestFactory.createUser("hans.peter");
        userService.save(user);

        ProjectMember projectMember = TestFactory.createProjectMember();
        projectMember.setUser(user);

        Project project = TestFactory.createProject();
        project.getProjectTeam().add(projectMember);

        projectService.save(project);

        List<Project> usersProjects = projectService.findByUser(user.getId());
        assertFalse(usersProjects.isEmpty());
        assertTrue(usersProjects.size() == 1);
        assertTrue(usersProjects.get(0).getId().equals(project.getId()));
    }
}
