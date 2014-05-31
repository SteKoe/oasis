package de.stekoe.idss;

import javax.inject.Inject;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.session.WebSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:web-ui/TestBeanLocations.xml", "classpath:services/TestBeanLocations.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public abstract class AbstractWicketApplicationTester {

    @Inject
    private FakeWebApplication fakeWebApplication;

    protected WicketTester wicketTester;

    @Before
    public void setup() {
        wicketTester = new WicketTester(fakeWebApplication);
    }

    protected WebSession getSession() {
        return (WebSession) wicketTester.getSession();
    }
}
