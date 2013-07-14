package de.stekoe.idss.page;

import static org.junit.Assert.*;

import java.util.Locale;

import org.apache.wicket.util.tester.WicketTester;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.WicketApplication;

public class HomePageTest {

	private static final Locale LOCALE = Locale.ENGLISH;
	private WicketTester tester;

	@Before
	public void setUp() {
		tester = new WicketTester(new TestWicketApplication());
		tester.getSession().setLocale(LOCALE);
	}

	@Test
	public void homepageRendersSuccessfully() throws Exception {
		tester.startPage(HomePage.class);
		tester.assertRenderedPage(HomePage.class);
	}

	@Test
	public void languageIsSet() throws Exception {
		tester.getSession().getLocale().equals(LOCALE);
	}

	@Test
	public void languageCorrect() throws Exception {
		RegistrationPage page = tester.startPage(RegistrationPage.class);
		String registerFormTitle = page.get("form").getString("form.title");
		assertThat(registerFormTitle, IsEqual.equalTo("Register"));
	}

	private class TestWicketApplication extends WicketApplication {
		@Override
		public void setUpSpring() {
		}
	}

}
