package de.stekoe.idss.page;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.service.UserService;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class HomePageTest extends AbstractWicketApplicationTester {

    @Inject
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        User user = TestFactory.createUser(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        user.setUserStatus(UserStatus.ACTIVATED);
        userService.save(user);

        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
    }

    @Test
    public void loggedOff() {
        wicketTester.startPage(HomePage.class);
    }

    @Test
    public void userNameIsShownInUserMenuPanel() {
        wicketTester.startPage(HomePage.class);
        wicketTester.assertLabel("nav.user:userProfile:username", TestFactory.USER_USERNAME);
    }
}
