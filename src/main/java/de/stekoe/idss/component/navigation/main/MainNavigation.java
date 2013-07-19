package de.stekoe.idss.component.navigation.main;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.page.LogoutPage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.UserProfilePage;

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
		
		NavbarButton<HomePage> homePage = createHomePageLink();
		NavbarButton<ContactPage> contactPage = createContactPageLink();
		NavbarButton<RegistrationPage> registrationPage = createRegistrationPageLink();
		NavbarButton<LoginPage> loginPage = createLoginPageLink();
		NavbarButton<UserProfilePage> profilePage = createUserProfilePageLink();
		NavbarButton<LogoutPage> logoutPage = createLogoutPageLink();
		
		List<INavbarComponent> navbarComponent = NavbarComponents.transform(Navbar.ComponentPosition.LEFT, 
				homePage, 
				contactPage, 
				registrationPage,
				profilePage,
				loginPage,
				logoutPage
				);
		navbar.addComponents(navbarComponent);
		return navbar;
	}

	private NavbarButton<LogoutPage> createLogoutPageLink() {
		NavbarButton<LogoutPage> userProfilePage = new NavbarButton<LogoutPage>(LogoutPage.class, Model.of("Abmelden"));
		userProfilePage.setIconType(IconType.user);
		if(!IDSSSession.get().isLoggedIn()) {
			userProfilePage.setVisible(false);
		}
		return userProfilePage;
	}
	
	private NavbarButton<UserProfilePage> createUserProfilePageLink() {
		NavbarButton<UserProfilePage> userProfilePage = new NavbarButton<UserProfilePage>(UserProfilePage.class, Model.of("Profile"));
		userProfilePage.setIconType(IconType.user);
		if(!IDSSSession.get().isLoggedIn()) {
			userProfilePage.setVisible(false);
		}
		return userProfilePage;
	}

	private NavbarButton<HomePage> createHomePageLink() {
		NavbarButton<HomePage> homePage = new NavbarButton<HomePage>(HomePage.class, Model.of("Home"));
		homePage.setIconType(IconType.home);
		return homePage;
	}

	private NavbarButton<ContactPage> createContactPageLink() {
		NavbarButton<ContactPage> contactPage = new NavbarButton<ContactPage>(ContactPage.class, Model.of("Contact"));
		contactPage.setIconType(IconType.questionsign);
		return contactPage;
	}

	private NavbarButton<RegistrationPage> createRegistrationPageLink() {
		NavbarButton<RegistrationPage> registrationPage = new NavbarButton<RegistrationPage>(RegistrationPage.class, Model.of("Registration"));
		registrationPage.setIconType(IconType.user);

		if(IDSSSession.get().isLoggedIn()) {
			registrationPage.setVisible(false);
		}
		return registrationPage;
	}

	private NavbarButton<LoginPage> createLoginPageLink() {
		NavbarButton<LoginPage> loginPage = new NavbarButton<LoginPage>(LoginPage.class, Model.of("Login"));
		loginPage.setIconType(IconType.user);
		
		if(IDSSSession.get().isLoggedIn()) {
			loginPage.setVisible(false);
		}
		
		return loginPage;
	}
}
