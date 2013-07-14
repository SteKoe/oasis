package de.stekoe.idss.component.navigation.main;

import java.util.List;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.RegistrationPage;

@SuppressWarnings("serial")
public class MainNavigation extends Panel {

	public MainNavigation(String id) {
		this(id, null);
	}
	
	public MainNavigation(String id, IModel<?> model) {
		super(id, model);
		
		add(buildMainNavigation());
	}

	private Navbar buildMainNavigation() {
		Navbar navbar = new Navbar("mainNavigation");
		navbar.setPosition(Navbar.Position.TOP);
		navbar.brandName(Model.of("IDSS"));
		
		NavbarButton<HomePage> homePage = new NavbarButton<HomePage>(HomePage.class, Model.of("Home"));
		homePage.setIconType(IconType.home);
		
		NavbarButton<ContactPage> contactPage = new NavbarButton<ContactPage>(ContactPage.class, Model.of("Contact"));
		contactPage.setIconType(IconType.questionsign);
		
		NavbarButton<RegistrationPage> registrationPage = new NavbarButton<RegistrationPage>(RegistrationPage.class, Model.of("Registration"));
		registrationPage.setIconType(IconType.user);
		
		List<INavbarComponent> navbarComponent = NavbarComponents.transform(Navbar.ComponentPosition.LEFT, homePage, contactPage, registrationPage);
		navbar.addComponents(navbarComponent);
		return navbar;
	}
}
