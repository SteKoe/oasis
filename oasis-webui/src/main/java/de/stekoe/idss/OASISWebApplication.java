/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import javax.inject.Inject;

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

import de.agilecoders.wicket.core.Bootstrap;
import de.agilecoders.wicket.core.settings.BootstrapSettings;
import de.stekoe.idss.page.AuthorizationStrategy;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.session.WebSession;

@Component
public class OASISWebApplication extends AuthenticatedWebApplication implements ApplicationContextAware {

    private static final Logger LOG = Logger.getLogger(OASISWebApplication.class);
    public static final Class<? extends WebPage> HOMEPAGE = HomePage.class;

    public static final Locale[] LANGUAGES = {
            Locale.GERMAN,
            Locale.ENGLISH,
            new Locale("pl", ""),
            Locale.ITALIAN,
            new Locale("es", "")
    };

    private ApplicationContext ctx;

    @Inject
    private ApplicationRoutes applicationRoutes;

    public static OASISWebApplication get() {
        return (OASISWebApplication) Application.get();
    }

    @Override
    public void init() {
        super.init();

        new BeanValidationConfiguration().configure(this);
        getJavaScriptLibrarySettings().setJQueryReference(new UrlResourceReference(Url.parse("//code.jquery.com/jquery-1.10.2.min.js")));
        setConfigurationType();
        setSecuritySettings();
        setUpSpring();
        getMarkupSettings().setDefaultMarkupEncoding(StandardCharsets.UTF_8.displayName());
        Bootstrap.install(this, new BootstrapSettings());

        LOG.info("Found following beans:");
        for(String beanName : ctx.getBeanDefinitionNames()) {
            Object bean = ctx.getBean(beanName);
            LOG.info("  '" + beanName + "': " + bean);
        }

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
//                IRequestMapper cryptoMapper = new CryptoMapper(getRootRequestMapper(), this);
//                setRootRequestMapper(cryptoMapper);
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