package de.stekoe.idss;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.page.ActivateUserPage;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.auth.user.UserProfilePage;
import de.stekoe.idss.page.error.Error403Page;
import de.stekoe.idss.page.error.Error404Page;
import de.stekoe.idss.page.error.Error410Page;
import de.stekoe.idss.page.error.Error500Page;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see de.stekoe.idss.Start#main(String[])
 */
public class IDSSApplication extends AuthenticatedWebApplication {

    /** Languages available for this application. */
    public static final Locale[] LANGUAGES = {
        Locale.GERMAN
    };

    @Override
    public void init() {
        super.init();

        setConfigurationType();

        setSecuritySettings();

        configureBootstrapFramework();
        setUpSpring();
        createURLRoutings();

        // HTML Status Pages
        set403Page();
        set404Page();
        set410Page();
        set500Page();
    }

    private void set410Page() {
        getApplicationSettings().setPageExpiredErrorPage(Error410Page.class);
    }

    private void set500Page() {
        getApplicationSettings().setInternalErrorPage(Error500Page.class);
    }

    private void set403Page() {
        getApplicationSettings().setAccessDeniedPage(Error403Page.class);
    }

    private void set404Page() {
        mountPage("/404", Error404Page.class);
    }

    private void setConfigurationType() {
        switch (getConfigurationType()) {
            case DEVELOPMENT:
                getRequestLoggerSettings().setRequestLoggerEnabled(true);
                getDebugSettings().setDevelopmentUtilitiesEnabled(false);
                getDebugSettings().setOutputComponentPath(true);
                break;
            case DEPLOYMENT:
            default:
                getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
                getMarkupSettings().setCompressWhitespace(true);
        }
    }

    private void setSecuritySettings() {
        getSecuritySettings().setAuthorizationStrategy(new IDSSAuthorizationStrategy());
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new IDSSSession(request);
    }

    private void configureBootstrapFramework() {
        Bootstrap.install(Application.get(), new BootstrapSettings());
    }

    /**
     * Set up Spring Component Injector
     */
    public void setUpSpring() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));
    }

    private void createURLRoutings() {
        mountPage("/home", HomePage.class);
        mountPage("/contact", ContactPage.class);
        mountPage("/register", RegistrationPage.class);
        mountPage("/activate", ActivateUserPage.class);
        mountPage("/profile", UserProfilePage.class);
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return IDSSSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }
}
