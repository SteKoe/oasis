package de.stekoe.idss;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.bean.validation.BeanValidationConfiguration;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.IExceptionSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.page.AuthorizationStrategy;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.rest.CriterionPageRestResource;
import de.stekoe.idss.session.WebSession;

@Component
public class OASISWebApplication extends AuthenticatedWebApplication implements ApplicationContextAware {

    private static final Logger LOG = Logger.getLogger(OASISWebApplication.class);
    public static final Class<? extends WebPage> HOMEPAGE = HomePage.class;
    public static final Map<Locale, String> LANGUAGES = new HashMap<Locale, String>();

    private ApplicationContext ctx;

    @Inject
    private ApplicationRoutes applicationRoutes;

    @Inject
    CriterionPageRestResource criterionPageRestResource;

    public static OASISWebApplication get() {
        return (OASISWebApplication) Application.get();
    }

    @Override
    public void init() {
        super.init();

        LANGUAGES.put(Locale.GERMAN, "Deutsch");
        LANGUAGES.put(Locale.ENGLISH, "English");
        LANGUAGES.put(new Locale("pl", ""), "Polski");
        LANGUAGES.put(Locale.ITALIAN, "Italiano");
        LANGUAGES.put(new Locale("es", ""), "Español");

        mountResource("/rest/criterionpage", new ResourceReference("criterionPageRestReference") {
            @Override
            public IResource getResource() {
                return criterionPageRestResource;
            }
        });

        new BeanValidationConfiguration().configure(this);
        setConfigurationType();
        setSecuritySettings();
        setUpSpring();
        getMarkupSettings().setDefaultMarkupEncoding(StandardCharsets.UTF_8.displayName());
        BootstrapSettings settings = new BootstrapSettings();
        Bootstrap.install(this, settings);

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
                break;
        }
    }

    private void setSecuritySettings() {
        getSecuritySettings().setAuthorizationStrategy(new AuthorizationStrategy());
    }

    @Override
    public org.apache.wicket.Session newSession(Request request, Response response) {
        return new WebSession(request);
    }

    public void setUpSpring() {
        getComponentInstantiationListeners().add(new SpringComponentInjector(this, ctx));
    }

    @Override
    public Class<? extends WebPage> getHomePage() {
        return HOMEPAGE;
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