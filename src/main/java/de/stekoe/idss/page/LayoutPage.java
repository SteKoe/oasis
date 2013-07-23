package de.stekoe.idss.page;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.component.navigation.language.LanguageSwitcher;
import de.stekoe.idss.component.navigation.main.MainNavigation;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class LayoutPage extends WebPage {
    private static final long serialVersionUID = 1860769875900411155L;

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
        createContent();
    }

    private void configureSession() {
        getSession().bind();
    }

    private void createContent() {
        add(new MyFencedFeedbackPanel("systemmessages"));
        add(new MainNavigation("navbar"));
        add(new LanguageSwitcher("languages"));
    }

    @Override
    public IDSSSession getSession() {
        return IDSSSession.get();
    }
}
