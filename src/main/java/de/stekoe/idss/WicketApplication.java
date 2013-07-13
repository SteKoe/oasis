package de.stekoe.idss;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.RegistrationPage;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see de.stekoe.idss.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return HomePage.class;
	}

	@Override
	public void init()
	{
		super.init();
		Bootstrap.install(Application.get(), new BootstrapSettings());
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		
		createURLRoutings();
	}

	private void createURLRoutings() {
		mountPage("/home", HomePage.class);
		mountPage("/contact", ContactPage.class);
		mountPage("/register", RegistrationPage.class);
	}
}
