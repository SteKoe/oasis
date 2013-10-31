package de.stekoe.idss;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.auth.AuthorizationStrategy;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.page.auth.LogoutPage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.error.Error403Page;
import de.stekoe.idss.page.error.Error404Page;
import de.stekoe.idss.page.error.Error410Page;
import de.stekoe.idss.page.error.Error500Page;
import de.stekoe.idss.page.project.CreateProjectPage;
import de.stekoe.idss.page.project.ProjectDetailsPage;
import de.stekoe.idss.page.project.ProjectEditPage;
import de.stekoe.idss.page.project.ProjectOverviewPage;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.page.user.UserDetailsPage;
import de.stekoe.idss.page.user.UserProfilePage;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.core.request.mapper.CryptoMapper;
import org.apache.wicket.core.util.crypt.KeyInSessionSunJceCryptFactory;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import java.util.Locale;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 *
 * @see de.stekoe.idss.Start#main(String[])
 */
public class WebApplication extends AuthenticatedWebApplication {

    private static Logger LOG = Logger.getLogger(WebApplication.class);

    @SpringBean(name = "systemRoleDAO")
    private ISystemRoleDAO systemRoleDAO;

    /** Languages available for this application. */
    public static final Locale[] LANGUAGES = {
        Locale.GERMAN
    };

    @Override
    public void onEvent(IEvent<?> event) {
        LOG.info(event.toString());
        super.onEvent(event);
    }

    @Override
    public void init() {
        super.init();

        new BeanValidationConfiguration().configure(this);

        setConfigurationType();

        setSecuritySettings();

        configureBootstrapFramework();
        setUpSpring();
        createRoutes();

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
        getSecuritySettings().setAuthorizationStrategy(new AuthorizationStrategy());
    }

    @Override
    public org.apache.wicket.Session newSession(Request request, Response response) {
        return new WebSession(request);
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

    private void createRoutes() {
        mountPage("/home", HomePage.class);
        mountPage("/contact", ContactPage.class);

        // Login, Logout, Register, ...
        mountPage("/register", RegistrationPage.class);
        mountPage("/login", LoginPage.class);
        mountPage("/activate", ActivateUserPage.class);
        mountPage("/logout", LogoutPage.class);

        // User Profile
        mountPage("/profile", UserProfilePage.class);
        mountPage("/profile/${id}", UserDetailsPage.class);

        // Projects
        mountPage("/project", ProjectOverviewPage.class);
        mountPage("/project/create", CreateProjectPage.class);
        mountPage("/project/show/${id}", ProjectDetailsPage.class);
        mountPage("/project/edit/${id}", ProjectEditPage.class);
        mountPage("/project/delete/${id}", ProjectEditPage.class);
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
        return WebSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass() {
        return LoginPage.class;
    }


}
