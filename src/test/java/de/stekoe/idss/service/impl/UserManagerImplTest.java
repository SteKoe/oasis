package de.stekoe.idss.service.impl;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.WicketApplication;
import de.stekoe.idss.dao.BaseTest;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;
import de.stekoe.idss.service.IUserService.LoginStatus;

public class UserManagerImplTest extends BaseTest {

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
            user.setEmail(USERNAMES[i] + "@example.com");
            user.setPassword(BCrypt.hashpw(PASSWORT, BCrypt.gensalt()));
            userManager.insertUser(user);
        }
    }

    @Test
    public void getAllUsers() throws Exception {
        userManager.getAllUsers();
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void duplicatedUsername() throws Exception {
        User duplicatedUser = new User();
        duplicatedUser.setUsername(USERNAMES[0]);

        userManager.insertUser(duplicatedUser);
    }

    @Test
    public void loginWorks() throws Exception {
        LoginStatus loginStatus = userManager.login(USERNAMES[0], PASSWORT);
        assertTrue(IUserService.LoginStatus.SUCCESS.equals(loginStatus));
    }

    private class TestWicketApplication extends WicketApplication {
        @Override
        public void setUpSpring() {
        }
    }

    @Test
    public void loginIfNotActivated() throws Exception {
        User user = new User();
        user.setUsername("unactivatedUser");
        user.setEmail("unactivatedUser@example.com");
        user.setPassword(BCrypt.hashpw("geheim", BCrypt.gensalt()));
        user.setActivationKey("test");
        userManager.insertUser(user);

        LoginStatus loginStatus = userManager.login("unactivatedUser", "geheim");
        assertTrue(IUserService.LoginStatus.USER_NOT_ACTIVATED.equals(loginStatus));

        user = userManager.findByUsername("unactivatedUser");
        user.setActivationKey(null);

        loginStatus = userManager.login("unactivatedUser", "geheim");
        assertTrue(IUserService.LoginStatus.SUCCESS.equals(loginStatus));
    }
}
