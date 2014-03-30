package de.stekoe.idss.service.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.repository.ProjectRepository;
import de.stekoe.idss.repository.UserRepository;
import de.stekoe.idss.service.AuthService;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultAuthServiceTest extends AbstractBaseTest {

    @Inject
    ProjectRepository projectRepository;

    @Inject
    AuthService authService;

    @Inject
    UserRepository userService;

    @Test(expected = NullPointerException.class)
    public void testNullArguments() {
        authService.authenticate(null, null);
    }

    @Test
    public void userSpecificAuth() throws Exception {
        final ProjectId projectId = new ProjectId();

        Set<Permission> permissionList = new HashSet<Permission>();
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, projectId));
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));

        User user = TestFactory.createUser(UUID.randomUUID().toString());
        user.setPermissions(permissionList);

        userService.save(user);

        Project project = TestFactory.createProject();
        project.setId(projectId);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);

        projectRepository.save(project);

        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.CREATE));
        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.READ));
        assertTrue(authService.isAuthorized(user.getId(), project, PermissionType.UPDATE));
        assertTrue(authService.isAuthorized(user.getId(), project, PermissionType.DELETE));
    }

    @Test
    public void userSpecificAuthFails() throws Exception {
        Set<Permission> permissionList = new HashSet<Permission>();
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, new ProjectId()));

        User user = new User();
        user.setPermissions(permissionList);

        Project project = new Project();
        project.setId(new ProjectId());

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);

        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.DELETE));
    }
}
