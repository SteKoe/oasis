package de.stekoe.idss.session;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.TestFactory;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class WebSessionTest extends AbstractWicketApplicationTester {

    @Test
    @DirtiesContext
    public void noUserLoggedIn() {
        assertFalse(getSession().isSignedIn());
    }

    @Test
    @DirtiesContext
    public void userLoggedIn() {
        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        assertTrue(getSession().isSignedIn());
    }

    @Test
    @DirtiesContext
    public void userLoggedOut() {
        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        getSession().invalidate();

        assertFalse(getSession().isSignedIn());
    }
}
