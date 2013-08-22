package de.stekoe.idss.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.component.navigation.language.LanguageSwitcher;
import de.stekoe.idss.component.navigation.main.MainNavigation;
import de.stekoe.idss.panel.UserPanel;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class LayoutPage extends WebPage {
    private static final long serialVersionUID = 1860769875900411155L;
    private MyFencedFeedbackPanel myFencedFeedbackPanel;

    /**
     * Construct.
     */
    public LayoutPage() {
        super();
        initPage();
    }

    /**
     * Construct.
     * @param model IModel of the page
     */
    public LayoutPage(IModel<?> model) {
        super(model);
        initPage();
    }

    /**
     * Construct.
     * @param parameters Wrapped page parameters
     */
    public LayoutPage(PageParameters parameters) {
        super(parameters);
        initPage();
    }

    /**
     * Set the page title.
     * @param pageTitle Must not be null
     */
    public void setTitle(String pageTitle) {
        add(new Label("pageTitle", Model.of(pageTitle)));
    }

    /**
     * Set the page title.
     * @param pageTitle Must not be null
     */
    public void setTitle(StringResourceModel pageTitle) {
        add(new Label("pageTitle", pageTitle));
    }

    @Override
    public IDSSSession getSession() {
        return IDSSSession.get();
    }

    private void initPage() {
        configureSession();

        setTitle(getString("application.title"));
        createContent();
    }

    private void configureSession() {
        getSession().bind();
    }

    private void createContent() {
        add(new MyFencedFeedbackPanel("systemmessages"));
        add(new MainNavigation("navbar"));
        add(new LanguageSwitcher("languages"));
        add(new UserPanel("userPanel"));
    }
}
