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

    private void initPage() {
        configureSession();

        setTitle(getString("application.title"));
        createContent();
    }

    public void setTitle(String pageTitle) {
        add(new Label("pageTitle", Model.of(pageTitle)));
    }

    public void setTitle(StringResourceModel pageTitle) {
        add(new Label("pageTitle", pageTitle));
    }

    private void configureSession() {
        getSession().bind();
    }

    private void createContent() {
        createFeedbackPanel();
        add(getGlobalFeedbackPanel());
        add(new MainNavigation("navbar"));
        add(new LanguageSwitcher("languages"));
        add(new UserPanel("userPanel"));
    }

    private void createFeedbackPanel() {
        myFencedFeedbackPanel = new MyFencedFeedbackPanel("systemmessages");
    }

    public MyFencedFeedbackPanel getGlobalFeedbackPanel() {
        return myFencedFeedbackPanel;
    }

    @Override
    public IDSSSession getSession() {
        return IDSSSession.get();
    }
}
