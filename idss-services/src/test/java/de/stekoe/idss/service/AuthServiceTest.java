package de.stekoe.idss.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.repository.ProjectRepository;
import de.stekoe.idss.repository.UserRepository;

public class AuthServiceTest extends AbstractBaseTest {

    private static final String PASSWORD = "password";

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
        Project project = TestFactory.createProject();

        Set<Permission> permissionList = new HashSet<Permission>();
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, project.getId()));
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, project.getId()));

        User user = TestFactory.createUser(UUID.randomUUID().toString());
        user.setPermissions(permissionList);

        userService.save(user);


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
        Project project = new Project();

        Set<Permission> permissionList = new HashSet<Permission>();
        permissionList.add(new Permission(PermissionObject.PROJECT, PermissionType.DELETE, project.getId()));

        User user = new User();
        user.setPermissions(permissionList);


        ProjectMember pm = new ProjectMember();
        pm.setUser(user);

        assertFalse(authService.isAuthorized(user.getId(), project, PermissionType.DELETE));
    }

    @Test
    public void loginWorks() throws Exception {
        User user = TestFactory.createUser(UUID.randomUUID().toString());
        user.setPassword(authService.hashPassword(PASSWORD));
        userService.save(user);

        user = userService.findByUsername(user.getUsername());
        user.setActivationKey(null);
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);

        assertTrue(authService.checkPassword(PASSWORD, user.getPassword()));

        AuthStatus authStatus = authService.authenticate(user.getUsername(), PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.SUCCESS));
    }

    @Test
    public void loginIfNotActivated() throws Exception {
        User user = TestFactory.createUser(UUID.randomUUID().toString());
        user.setPassword(authService.hashPassword(PASSWORD));
        userService.save(user);

        AuthStatus authStatus = authService.authenticate(user.getUsername(), PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.USER_NOT_ACTIVATED));

        user = userService.findByUsername(user.getUsername());
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);

        authStatus = authService.authenticate(user.getUsername(), PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.SUCCESS));
    }
}
