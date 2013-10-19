package de.stekoe.idss.service.impl;

import de.stekoe.idss.TestFactory;
import de.stekoe.idss.WebApplication;
import de.stekoe.idss.dao.BaseTest;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.service.IUserService;
import de.stekoe.idss.service.IUserService.LoginStatus;
import de.stekoe.idss.util.PasswordUtil;
import org.apache.log4j.Logger;
import org.apache.wicket.util.tester.WicketTester;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserManagerImplTestCase extends BaseTest {

    private static final String MAIL_EXAMPLE_COM = "mail@example.com";
    private static final String ACTIVATION_KEY = "ACTIVATION_KEY";
    private static final Logger LOG = Logger.getLogger(UserManagerImplTestCase.class);
    private static final String[] USERNAMES = { "Stephan", "Benedikt", "Robert", "Jonas" };
    private static final String PASSWORT = "geheimesPassword";

    @Autowired
    private IUserService userManager;

    @Before
    public void setUp() throws Exception {
        new WicketTester(new TestWicketApplication());

        for (int i = 0; i < USERNAMES.length; i++) {
            User user = new User();
            user.setUsername(USERNAMES[i]);
            user.setEmail(USERNAMES[i].toLowerCase() + "@example.com");
            user.setPassword(new PasswordUtil().hashPassword(PASSWORT));
            userManager.save(user);
        }

        List<User> allUsers = userManager.getAllUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            LOG.info(String.format("User [%s]: %s (%s)", i, allUsers.get(i).getUsername(), allUsers.get(i).getId().toString()));
        }
    }

    @Test
    public void getAllUsers() throws Exception {
        assertThat(userManager.getAllUsers().size(), Is.is(IsNot.not(0)));
    }

    @Test(expected=UsernameAlreadyInUseException.class)
    public void duplicatedUsername() throws Exception {
        User duplicatedUser = TestFactory.createUser(USERNAMES[0]);
        duplicatedUser.setEmail("iamunique@example.com");
        userManager.save(duplicatedUser);
    }

    @Test
    public void loginWorks() throws Exception {
        final User user = userManager.findByUsername(USERNAMES[0]);
        user.setActivationKey(null);
        user.setUserStatus(UserStatus.ACTIVATED);
        userManager.save(user);

        assertTrue(new PasswordUtil().checkPassword(PASSWORT, user.getPassword()));

        LoginStatus loginStatus = userManager.login(USERNAMES[0], PASSWORT);
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.SUCCESS));
    }

    private class TestWicketApplication extends WebApplication {
        @Override
        public void setUpSpring() {
        }
    }

    @Test
    public void loginIfNotActivated() throws Exception {
        LoginStatus loginStatus = userManager.login(USERNAMES[0], "geheim");
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.USER_NOT_ACTIVATED));

        User user = userManager.findByUsername(USERNAMES[0]);
        user.setUserStatus(UserStatus.ACTIVATED);
        userManager.save(user);

        loginStatus = userManager.login(USERNAMES[0], PASSWORT);
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.SUCCESS));
    }

    @Test
    public void lookupNotExistingUser() throws Exception {
        User user = userManager.findByUsername("i do not exist");
        assertNull(user);
    }
}
