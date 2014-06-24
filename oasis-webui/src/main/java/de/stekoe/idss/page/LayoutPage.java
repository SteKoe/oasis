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

package de.stekoe.idss.page;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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

        configureSession();

        addPageLogo();
        addPageTitle(getContextParameter("application.title"));
        addContentElements();
        addPageFooter();
        addDebugPanel();

        Label titleLabel = new Label("page.title", pageTitle);
        add(titleLabel);
        titleLabel.setVisible(!StringUtils.isBlank(pageTitle));
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

    private void addPageFooter() {
        String applicationName = getContextParameter("application.name");
        String applicationVersion = getContextParameter("application.version");

        add(new Label("application.footer", applicationName + " " + applicationVersion));
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
