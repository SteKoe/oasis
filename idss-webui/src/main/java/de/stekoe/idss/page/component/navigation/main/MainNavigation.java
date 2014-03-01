package de.stekoe.idss.page.component.navigation.main;

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
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
                Navbar.ComponentPosition.LEFT,
                createHomePageLink(),
                createContactPageLink(),
                createProjectOverviewPageLink());
        navbar.addComponents(navbarComponent);


        return navbar;
    }

    private NavbarButton<HomePage> createHomePageLink() {
        NavbarButton<HomePage> homePage = new NavbarButton<HomePage>(
                HomePage.class, Model.of("Home"));
        homePage.setIconType(GlyphIconType.home);
        return homePage;
    }

    private NavbarButton<ContactPage> createContactPageLink() {
        NavbarButton<ContactPage> contactPage = new NavbarButton<ContactPage>(
                ContactPage.class, Model.of("Kontakt"));
        contactPage.setIconType(GlyphIconType.questionsign);
        return contactPage;
    }

    private NavbarButton<RegistrationPage> createRegistrationPageLink() {
        NavbarButton<RegistrationPage> registrationPage = new NavbarButton<RegistrationPage>(
                RegistrationPage.class, Model.of("Registrieren"));
        registrationPage.setIconType(GlyphIconType.user);
        return registrationPage;
    }

    private NavbarButton<ProjectListPage> createProjectOverviewPageLink() {
        NavbarButton<ProjectListPage> projectOverview = new NavbarButton<ProjectListPage>(ProjectListPage.class, Model.of("Project overview"));
        projectOverview.setVisible(WebSession.get().getUser() != null);
        return projectOverview;
    }
}