package de.stekoe.idss.page;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.FakeWebSession;
import de.stekoe.idss.TestFactory;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class HomePageTest extends AbstractWicketApplicationTester {
    @Test
    @DirtiesContext
    public void loggedOff() {
        wicketTester.startPage(HomePage.class);
    }

    @Test
    @DirtiesContext
    public void userNameIsShownInUserMenuPanel() {
        ((FakeWebSession) wicketTester.getSession()).signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        wicketTester.startPage(HomePage.class);
        wicketTester.assertLabel("nav.user:userProfile:username", TestFactory.USER_USERNAME);
    }
}
