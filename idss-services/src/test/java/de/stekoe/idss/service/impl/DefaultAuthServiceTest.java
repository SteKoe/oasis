package de.stekoe.idss.service.impl;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultAuthServiceTest extends AbstractBaseTest {

    @Autowired AuthService authService;
    @Autowired ProjectService projectService;
    @Autowired UserService userService;

    @Test(expected = NullPointerException.class)
    public void testNullArguments() {
        authService.authenticate(null,null);
    }

    @Test
    public void userSpecificAuth() throws Exception {
        final String projectId = "P1234";

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

        projectService.save(project);

        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.CREATE));
        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.READ));
        assertTrue(authService.isAuthorized(user.getId(), project, PermissionType.UPDATE));
        assertTrue(authService.isAuthorized(user.getId(), project, PermissionType.DELETE));
    }

    @Test
    public void userSpecificAuthFails() throws Exception {
        Set<Permission> permissionList = new HashSet<Permission>();
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, "P1234"));

        User user = new User();
        user.setPermissions(permissionList);

        Project project = new Project();
        project.setId("P1235");

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);

        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.DELETE));
    }
}
