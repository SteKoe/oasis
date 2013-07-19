package de.stekoe.idss;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.RoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.page.AccessDeniedPage;
import de.stekoe.idss.page.ActivateUserPage;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.UserProfilePage;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see de.stekoe.idss.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    public void init() {
        super.init();
        switch (getConfigurationType()) {
        case DEVELOPMENT:
            getRequestLoggerSettings().setRequestLoggerEnabled(true);
            getDebugSettings().setDevelopmentUtilitiesEnabled(true);
            break;
        case DEPLOYMENT:
        default:
            getMarkupSettings().setCompressWhitespace(true);
        }

        getSecuritySettings().setAuthorizationStrategy(
                new RoleAuthorizationStrategy(new UserRolesAuthorizer()));

        configureBootstrap();
        setUpSpring();

        createURLRoutings();

        // getApplicationSettings().setPageExpiredErrorPage(MyExpiredPage.class);
        getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);
        // getApplicationSettings().setInternalErrorPage(MyInternalErrorPage.class);
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new IDSSSession(request);
    }

    private void configureBootstrap() {
        BootstrapSettings bootstrapSettings = new BootstrapSettings();
        bootstrapSettings.useCdnResources(true);
        Bootstrap.install(Application.get(), bootstrapSettings);
    }

    /**
     * Set up Spring Component Injector
     */
    public void setUpSpring() {
        getComponentInstantiationListeners().add(
                new SpringComponentInjector(this));
    }

    private void createURLRoutings() {
        mountPage("/home", HomePage.class);
        mountPage("/contact", ContactPage.class);
        mountPage("/register", RegistrationPage.class);
        mountPage("/activate", ActivateUserPage.class);
        mountPage("/profile", UserProfilePage.class);
    }
}
