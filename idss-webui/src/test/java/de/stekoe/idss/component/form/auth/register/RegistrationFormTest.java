package de.stekoe.idss.component.form.auth.register;

import de.stekoe.idss.TestWebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class RegistrationFormTest {

    private WicketTester tester;

    @Before
    public void setUp() throws Exception {
        tester = new WicketTester(new TestWebApplication());
    }

    @After
    public void tearDown() throws Exception {

    }
}
