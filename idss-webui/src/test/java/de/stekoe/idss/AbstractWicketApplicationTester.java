package de.stekoe.idss;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:web-ui/TestBeanLocations.xml", "classpath:services/BeanLocations.xml"})
@TransactionConfiguration
@Transactional
public abstract class AbstractWicketApplicationTester {
    @Autowired
    protected FakeWebApplication webApplication;

    protected WicketTester wicketTester;

    @Before
    public void setup() {
        wicketTester = new WicketTester(webApplication);
    }

    @After
    public void tearDown() {
        wicketTester = null;
    }

    protected FakeWebSession getSession() {
        return (FakeWebSession) wicketTester.getSession();
    }
}
