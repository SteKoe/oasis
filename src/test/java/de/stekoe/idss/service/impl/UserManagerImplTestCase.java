package de.stekoe.idss.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.wicket.util.tester.WicketTester;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.IDSSApplication;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.dao.BaseTest;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.service.IUserService;
import de.stekoe.idss.service.IUserService.LoginStatus;

public class UserManagerImplTestCase extends BaseTest {

    private static final String MAIL_EXAMPLE_COM = "mail@example.com";
    private static final String ACTIVATION_KEY = "ACTIVATION_KEY";
    private static final Logger LOG = Logger.getLogger(UserManagerImplTestCase.class);
    private static final String[] USERNAMES = { "Stephan", "Benedikt", "Robert", "Jonas" };
    private static final String PASSWORT = "geheim";

    @Autowired
    private IUserService userManager;

    @Before
    public void setUp() throws Exception {
        new WicketTester(new TestWicketApplication());

        for (int i = 0; i < USERNAMES.length; i++) {
            User user = new User();
            user.setUsername(USERNAMES[i]);
            user.setEmail(USERNAMES[i].toLowerCase() + "@example.com");
            user.setPassword(BCrypt.hashpw(PASSWORT, BCrypt.gensalt()));
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
        LoginStatus loginStatus = userManager.login(USERNAMES[0], PASSWORT);
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.SUCCESS));
    }

    private class TestWicketApplication extends IDSSApplication {
        @Override
        public void setUpSpring() {
        }
    }

    @Test
    public void loginIfNotActivated() throws Exception {
        String username = "unactivatedUser";

        User user = new User();
        user.setUsername(username);
        user.setEmail("unactivatedUser@example.com");
        Set<Role> noRoles = Collections.emptySet();
        user.setRoles(noRoles);
        user.setUserProfile(new UserProfile());
        user.setPassword(BCrypt.hashpw(PASSWORT, BCrypt.gensalt()));
        user.setActivationKey(ACTIVATION_KEY);
        userManager.save(user);

        LoginStatus loginStatus = userManager.login(username, "geheim");
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.USER_NOT_ACTIVATED));

        user = userManager.findByUsername(username);
        user.setActivationKey(null);
        assertTrue(userManager.save(user));

        loginStatus = userManager.login("unactivatedUser", PASSWORT);
        assertThat(loginStatus, IsEqual.equalTo(LoginStatus.SUCCESS));
    }

    @Test
    public void lookupNotExistingUser() throws Exception {
        User user = userManager.findByUsername("i do not exist");
        assertNull(user);
    }
}
