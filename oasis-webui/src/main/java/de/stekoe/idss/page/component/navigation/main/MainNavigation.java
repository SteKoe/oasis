package de.stekoe.idss.page.component.navigation.main;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.CssClassNameModifier;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;

/**
 * @author Stephan Koeninger
 */
@SuppressWarnings("serial")
public class MainNavigation extends Panel {

    /**
     * @param id The id of this component
     */
    public MainNavigation(String id) {
        this(id, null);
    }

    /**
     * @param id    The id of this component
     * @param model The model of this component
     */
    public MainNavigation(String id, IModel<?> model) {
        super(id, model);
        add(buildMainNavigation());
    }

    private Navbar buildMainNavigation() {
        Navbar navbar = new Navbar("mainNavigation");
        navbar.setPosition(Navbar.Position.DEFAULT);

        List<INavbarComponent> navbarComponent = NavbarComponents.transform(
                Navbar.ComponentPosition.RIGHT,
                createHomePageLink(),
                createContactPageLink());
        navbar.addComponents(navbarComponent);
        navbar.add(new CssClassNameModifier("navbar navbar-oasis"));
        Component container = navbar.get("container");
        container.add(new CssClassNameModifier("container-fluid"));
        return navbar;
    }

    private NavbarButton<HomePage> createHomePageLink() {
        NavbarButton<HomePage> homePage = new NavbarButton<HomePage>(HomePage.class, new ResourceModel("label.home"));
        return homePage;
    }

    private NavbarButton<ContactPage> createContactPageLink() {
        NavbarButton<ContactPage> contactPage = new NavbarButton<ContactPage>(
                ContactPage.class, new ResourceModel("label.contact"));
        return contactPage;
    }
}
