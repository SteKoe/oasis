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

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class MainNavigation extends Panel {

    private NavbarButton<HomePage> homePage;
    private NavbarButton<ContactPage> contactPage;

    /**
     * @param id The id of this component
     */
    public MainNavigation(String id) {
        this(id, null);
    }

    /**
     * @param id The id of this component
     * @param model The model of this component
     */
    public MainNavigation(String id, IModel<?> model) {
        super(id, model);
        add(buildMainNavigation());
    }

    private Navbar buildMainNavigation() {
        Navbar navbar = new Navbar("mainNavigation");
        navbar.setPosition(Navbar.Position.DEFAULT);

        createPageLinks();

        togglePageLinksVisibility();

        List<INavbarComponent> navbarComponent = NavbarComponents.transform(
                Navbar.ComponentPosition.LEFT, homePage, contactPage);
        navbar.addComponents(navbarComponent);


        return navbar;
    }

    private void createPageLinks() {
        homePage = createHomePageLink();
        contactPage = createContactPageLink();
    }

    private NavbarButton<HomePage> createHomePageLink() {
        NavbarButton<HomePage> homePage = new NavbarButton<HomePage>(
                HomePage.class, Model.of("Home"));
        homePage.setIconType(IconType.home);
        return homePage;
    }

    private NavbarButton<ContactPage> createContactPageLink() {
        NavbarButton<ContactPage> contactPage = new NavbarButton<ContactPage>(
                ContactPage.class, Model.of("Kontakt"));
        contactPage.setIconType(IconType.questionsign);
        return contactPage;
    }

    private NavbarButton<RegistrationPage> createRegistrationPageLink() {
        NavbarButton<RegistrationPage> registrationPage = new NavbarButton<RegistrationPage>(
                RegistrationPage.class, Model.of("Registrieren"));
        registrationPage.setIconType(IconType.user);
        return registrationPage;
    }


    private void togglePageLinksVisibility() {
    }
}
