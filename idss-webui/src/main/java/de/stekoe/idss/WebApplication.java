package de.stekoe.idss;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.agilecoders.wicket.core.settings.DefaultThemeProvider;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.auth.AuthorizationStrategy;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.theme.BootstrapTheme;
import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class WebApplication extends AuthenticatedWebApplication implements ApplicationContextAware {

    private static Logger LOG = Logger.getLogger(WebApplication.class);

    /** Languages available for this application. */
    public static final Locale[] LANGUAGES = {
        Locale.GERMAN,
        Locale.ENGLISH
    };

    private ApplicationContext ctx;

    @Inject
    private ApplicationRoutes applicationRoutes;

    public static WebApplication get() {
        return (WebApplication) Application.get();
    }

    @Override
    public void init() {
        super.init();

        new BeanValidationConfiguration().configure(this);

        getJavaScriptLibrarySettings().setJQueryReference(new UrlResourceReference(Url.parse("//code.jquery.com/jquery-1.10.2.min.js")));
        setConfigurationType();

        setSecuritySettings();

        configureBootstrapFramework();
        setUpSpring();

        getMarkupSettings().setDefaultMarkupEncoding(StandardCharsets.UTF_8.displayName());

        applicationRoutes.create();
    }

    private void setConfigurationType() {
        switch (getConfigurationType()) {
            case DEVELOPMENT:
                getRequestLoggerSettings().setRequestLoggerEnabled(true);
                getDebugSettings().setDevelopmentUtilitiesEnabled(true);
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
        Bootstrap.install(this, new BootstrapSettings());

        final DefaultThemeProvider themeProvider = (DefaultThemeProvider) Bootstrap.getSettings().getThemeProvider();
        themeProvider.add(new BootstrapTheme());
    }

    public void setUpSpring() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}