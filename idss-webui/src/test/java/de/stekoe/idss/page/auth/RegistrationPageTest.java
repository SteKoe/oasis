package de.stekoe.idss.page.auth;

import de.stekoe.idss.AbstractWicketApplicationTester;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class RegistrationPageTest extends AbstractWicketApplicationTester {
    @Test
    @DirtiesContext
    public void test() {
        wicketTester.startPage(RegistrationPage.class);
    }
}
