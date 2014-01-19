package de.stekoe.idss.page;

import de.agilecoders.wicket.core.Bootstrap;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.component.navigation.language.LanguageSwitcher;
import de.stekoe.idss.component.navigation.main.MainNavigation;
import de.stekoe.idss.component.navigation.user.UserPanel;
import de.stekoe.idss.model.User;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.theme.BootstrapTheme;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public abstract class LayoutPage extends WebPage {

    public LayoutPage() {
        super();
    }

    public LayoutPage(IModel<?> model) {
        super(model);
    }

    public LayoutPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * Set the page title.
     *
     * @param pageTitle Must not be null
     */
    public void addPageTitle(String pageTitle) {
        add(new Label("pageTitle", Model.of(pageTitle)));
    }

    /**
     * Set the page title.
     *
     * @param pageTitle Must not be null
     */
    public void setTitle(StringResourceModel pageTitle) {
        add(new Label("pageTitle", pageTitle));
    }

    @Override
    public WebSession getSession() {
        return WebSession.get();
    }

    public User getUser() {
        return getSession().getUser();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        configureSession();

        Bootstrap.getSettings().getActiveThemeProvider().setActiveTheme(new BootstrapTheme());

        addPageLogo();
        addPageTitle(getString("application.title"));
        addContentElements();
        addDebugPanel();
    }

    private void addPageLogo() {
        add(new Image("logo", new ContextRelativeResource("/img/logo.png")));
    }

    private void addDebugPanel() {
        if (getApplication().getDebugSettings().isDevelopmentUtilitiesEnabled()) {
            DebugBar debugBar = new DebugBar("dev");
            add(debugBar);
        } else {
            add(new EmptyPanel("dev").setVisible(false));
        }
    }

    private void configureSession() {
        getSession().bind();
    }

    private void addContentElements() {
        add(new MyFencedFeedbackPanel("systemmessages", new IFeedbackMessageFilter() {
            @Override
            public boolean accept(FeedbackMessage message) {
                if (message.getReporter() instanceof FormComponent) {
                    return false;
                }
                return true;
            }
        }));
        add(new MainNavigation("nav.main"));
        add(new LanguageSwitcher("nav.lang"));
        add(new UserPanel("nav.user"));
    }
}
