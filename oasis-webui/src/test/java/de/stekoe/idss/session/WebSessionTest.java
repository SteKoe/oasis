package de.stekoe.idss.session;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.service.UserService;

/**
 * @author Stephan Koeninger 
 */
public class WebSessionTest extends AbstractWicketApplicationTester {

    @Inject
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        User user = TestFactory.createUser(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);
    }

    @Test
    @DirtiesContext
    public void noUserLoggedIn() {
        assertFalse(getSession().isSignedIn());
    }

    @Test
    @DirtiesContext
    public void userLoggedIn() {
        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        assertThat(getSession().isSignedIn(), is(equalTo(true)));
    }

    @Test
    @DirtiesContext
    public void userLoggedOut() {
        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        getSession().invalidate();

        assertThat(getSession().isSignedIn(), is(not(true)));
    }
}
