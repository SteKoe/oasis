package de.stekoe.idss.page;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.google.protobuf.Message;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.DropDownButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuBookmarkablePageLink;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuDivider;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.dropdown.MenuHeader;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.ImmutableNavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton;
import de.agilecoders.wicket.core.settings.IBootstrapSettings;
import de.agilecoders.wicket.core.settings.ITheme;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.button.DropDownAutoOpen;

public abstract class LayoutPage extends WebPage {
	private static final long serialVersionUID = 1860769875900411155L;

	public LayoutPage() {
		super();
		createContents();
	}

	public LayoutPage(IModel<?> model) {
		super(model);
		createContents();
	}

	public LayoutPage(PageParameters parameters) {
		super(parameters);
		createContents();
	}

	private void createContents() {
		add(createMainNavigation("navbar"));
	}

	private Navbar createMainNavigation(String id) {
		Navbar navbar = new Navbar(id);
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
