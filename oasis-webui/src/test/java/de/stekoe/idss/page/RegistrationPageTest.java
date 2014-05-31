package de.stekoe.idss.page;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.page.RegistrationPage;

public class RegistrationPageTest extends AbstractWicketApplicationTester {
    @Test
    @DirtiesContext
    public void test() {
        wicketTester.startPage(RegistrationPage.class);
    }
}
