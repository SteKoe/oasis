package de.stekoe.idss.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Test;

/**
 * Since Layoutpage is abstract all tests will be run with HomePage.class
 */
public class LayoutPageTestCase {

    private WicketTester tester;

    @Test
    public void pageTitleIsSet() {
        TestIDSSApplication testIDSSApplication = new TestIDSSApplication();
        tester = new WicketTester(testIDSSApplication);
        tester.startPage(HomePage.class);
        Label label = (Label) tester.getComponentFromLastRenderedPage("pageTitle");
        tester.assertLabel("pageTitle", label.getString("application.title"));
        tester.assertRenderedPage(HomePage.class);
    }

}
