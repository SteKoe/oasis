package de.stekoe.idss.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.User;

public class UserServiceTest extends AbstractBaseTest {

    private static final Logger LOG = Logger.getLogger(UserServiceTest.class);
    private static final String[] USERNAMES = {"Stephan", "Benedikt", "Robert", "Jonas"};
    private static final String PASSWORD = "geheimesPassword";

    @Inject
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < USERNAMES.length; i++) {
            User user = new User();
            user.setUsername(USERNAMES[i]);
            user.setEmail(USERNAMES[i].toLowerCase() + "@example.com");
            user.setPassword("a password not hashed..");
            userService.save(user);
        }
    }

    @After
    public void tearDown() {
        for (int i = 0; i < USERNAMES.length; i++) {
            final User userToDelete = userService.findByUsername(USERNAMES[i]);
            if (userToDelete != null) {
                userService.delete(userToDelete);
            }
        }
    }

    @Test
    public void getAllUsers() throws Exception {
        List<User> allUsers = userService.findAll();
        assertThat(allUsers.size(), Is.is(IsNot.not(0)));
    }

    @Ignore
    @Test(expected = DataIntegrityViolationException.class)
    public void duplicatedUsername() throws Exception {
        User duplicatedUser = TestFactory.createUser(USERNAMES[0]);
        duplicatedUser.setEmail("iamunique@example.com");
        userService.save(duplicatedUser);
    }

    @Test
    public void lookupNotExistingUser() throws Exception {
        User user = userService.findByUsername("i do not exist");
        assertNull(user);
    }
}
