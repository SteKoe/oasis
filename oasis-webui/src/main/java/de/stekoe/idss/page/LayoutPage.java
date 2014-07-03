package de.stekoe.idss.page;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.OASISWebApplication;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.page.component.navigation.language.LanguageSwitcher;
import de.stekoe.idss.page.component.navigation.main.MainNavigation;
import de.stekoe.idss.session.WebSession;

/**
 * Provides the overall layout which encloses the header and footer in
 * flexible div container. The content has no size and will stretch over the full width.
 *
 * |----- window width -----|
 * |                        |
 * |     |-- header --|     |
 * |                        |
 * |------- content --------|
 * |                        |
 * |     |-- footer --|     |
 */
public abstract class LayoutPage extends WebPage {
    private static final Logger LOG = Logger.getLogger(LayoutPage.class);

    private String pageTitle;

    public LayoutPage() {
        super();
    }

    public LayoutPage(IModel<?> model) {
        super(model);
    }

    public LayoutPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


        Url relative = Url.parse(getRequest().getContextPath());
        String baseUrl = getRequestCycle().getUrlRenderer().renderFullUrl(relative);

        WebMarkupContainer metaBaseUrl = new WebMarkupContainer("meta.baseUrl");
        add(metaBaseUrl);
        metaBaseUrl.add(new AttributeModifier("content", baseUrl.toString()));

        configureSession();

        addPageLogo();
        addPageTitle(getContextParameter("application.title"));
        addContentElements();
        addDebugPanel();

        addFooter();

        Label titleLabel = new Label("page.title", pageTitle);
        add(titleLabel);
        titleLabel.setVisible(!StringUtils.isBlank(pageTitle));
    }

    private void addFooter() {
        addDisclaimerLink();
    }

    private void addDisclaimerLink() {
        add(new BookmarkablePageLink<DisclaimerPage>("link.disclaimer", DisclaimerPage.class));
    }

    /**
     * Set the page title.
     *
     * @param pageTitle Must not be null
     */
    public void addPageTitle(String pageTitle) {
        add(new Label("pageTitle", Model.of(pageTitle)));
    }

    public String getContextParameter(String key) {
        return OASISWebApplication.get().getServletContext().getInitParameter(key);
    }

    public void setTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @Override
    public WebSession getSession() {
        return WebSession.get();
    }

    public User getUser() {
        return getSession().getUser();
    }

    private void addPageLogo() {
        final BookmarkablePageLink<HomePage> logoLink = new BookmarkablePageLink<HomePage>("logo.link", getApplication().getHomePage());
        add(logoLink);
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
