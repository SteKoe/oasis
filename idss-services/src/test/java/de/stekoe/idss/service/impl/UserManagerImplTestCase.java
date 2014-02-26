package de.stekoe.idss.service.impl;

import de.stekoe.idss.TestFactory;
import de.stekoe.idss.dao.BaseTest;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.service.UserService;
import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserManagerImplTestCase extends BaseTest {

    private static final Logger LOG = Logger.getLogger(UserManagerImplTestCase.class);
    private static final java.lang.String[] USERNAMES = { "Stephan", "Benedikt", "Robert", "Jonas" };
    private static final java.lang.String PASSWORD = "geheimesPassword";

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < USERNAMES.length; i++) {
            User user = new User();
            user.setUsername(USERNAMES[i]);
            user.setEmail(USERNAMES[i].toLowerCase() + "@example.com");
            user.setPassword(authService.hashPassword(PASSWORD));
            userService.save(user);
        }
    }

    @After
    public void tearDown() {
        for (int i = 0; i < USERNAMES.length; i++) {
            final User userToDelete = userService.findByUsername(USERNAMES[i]);
            if(userToDelete != null) {
                userService.delete(userToDelete);
            }
        }
    }

    @Test
    public void getAllUsers() throws Exception {
        assertThat(userService.getAllUsers().size(), Is.is(IsNot.not(0)));
    }

    @Test(expected=UsernameAlreadyInUseException.class)
    public void duplicatedUsername() throws Exception {
        User duplicatedUser = TestFactory.createUser(USERNAMES[0]);
        duplicatedUser.setEmail("iamunique@example.com");
        userService.save(duplicatedUser);
    }

    @Test
    public void loginWorks() throws Exception {
        final User user = userService.findByUsername(USERNAMES[0]);
        user.setActivationKey(null);
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);

        assertTrue(authService.checkPassword(PASSWORD, user.getPassword()));

        AuthStatus authStatus = authService.authenticate(USERNAMES[0], PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.SUCCESS));
    }

    @Test
    public void loginIfNotActivated() throws Exception {
        AuthStatus authStatus = authService.authenticate(USERNAMES[0], PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.USER_NOT_ACTIVATED));

        User user = userService.findByUsername(USERNAMES[0]);
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);

        authStatus = authService.authenticate(USERNAMES[0], PASSWORD);
        assertThat(authStatus, IsEqual.equalTo(AuthStatus.SUCCESS));
    }

    @Test
    public void lookupNotExistingUser() throws Exception {
        User user = userService.findByUsername("i do not exist");
        assertNull(user);
    }
}
