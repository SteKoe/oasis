package de.stekoe.idss.page.auth;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import de.stekoe.idss.AbstractWicketApplicationTester;

public class RegistrationPageTest extends AbstractWicketApplicationTester {
    @Test
    @DirtiesContext
    public void test() {
        wicketTester.startPage(RegistrationPage.class);
    }
}
