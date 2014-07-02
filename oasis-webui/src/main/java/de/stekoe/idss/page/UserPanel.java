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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.page.company.CompanyListPage;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.page.project.criterion.referencecatalog.ReferenceCriterionListPage;
import de.stekoe.idss.page.user.EditPasswordPage;
import de.stekoe.idss.page.user.EditUserProfilePage;
import de.stekoe.idss.session.WebSession;

/**
 * Panel shown when user is logged in.
 */
@SuppressWarnings("serial")
public class UserPanel extends Panel {

    private static final String USER_MENU_ITEM_LINK = "user.menu.item.link";
    private String username = "N/A";

    /**
     * @param id wicket:id
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public UserPanel(String id) {
        super(id);

        final User currentUser = WebSession.get().getUser();
        if (currentUser != null) {
            username = currentUser.getUsername();

            UserProfile profile = currentUser.getProfile();
            if(profile != null && profile.getFullName() != null) {
                username = profile.getFullName();
            }
        }

        createLoggedInPanel();
        createLoggedOutPanel();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void createLoggedInPanel() {
        final BookmarkablePageLink<EditUserProfilePage> userProfileLink = new BookmarkablePageLink<EditUserProfilePage>("userProfile", EditUserProfilePage.class);
        userProfileLink.add(new Label("username", username));

        add(userProfileLink);
        add(new BookmarkablePageLink("signout", LogoutPage.class) {
            @Override
            public boolean isVisible() {
                return WebSession.get().isSignedIn();
            }
        });

        List<MenuItem> menuItems = getUserMenuLinks();

        add(new ListView<MenuItem>("user.menu.item", menuItems) {
            @Override
            protected void populateItem(ListItem<MenuItem> item) {
                MenuItem menuItem = item.getModelObject();

                WebMarkupContainer link = new WebMarkupContainer(USER_MENU_ITEM_LINK);

                Label label = new Label("user.menu.item.label");

                if(menuItem instanceof MenuLink) {
                    MenuLink menuLink = (MenuLink) menuItem;
                    link = menuLink.getPage();
                    link.removeAll();
                    link.add(new Label("user.menu.item.link.label", getString(menuLink.getLabel())));
                } else if(menuItem instanceof MenuSeparator) {
                    item.add(new AttributeModifier("class", "divider"));
                    link.setVisible(false);
                    label = new Label("user.menu.item.label", "");
                } else if(menuItem instanceof MenuTitle) {
                    item.add(new AttributeModifier("class", "dropdown-header"));
                    link.setVisible(false);
                    label = new Label("user.menu.item.label", ((MenuTitle) menuItem).getTitle());
                }

                item.add(label);
                item.add(link);
            }
        });
    }

    private List<MenuItem> getUserMenuLinks() {
        List<MenuItem> linkList = new ArrayList<MenuItem>();

        linkList.add(new MenuLink("label.user.profile", new BookmarkablePageLink<EditUserProfilePage>(USER_MENU_ITEM_LINK, EditUserProfilePage.class)));
        linkList.add(new MenuLink("label.change.account.settings", new BookmarkablePageLink<EditPasswordPage>(USER_MENU_ITEM_LINK, EditPasswordPage.class)));
        linkList.add(new MenuSeparator());
        linkList.add(new MenuLink("label.project.manager", new BookmarkablePageLink<ProjectListPage>(USER_MENU_ITEM_LINK, ProjectListPage.class)));
        linkList.add(new MenuLink("label.company.manager", new BookmarkablePageLink<CompanyListPage>(USER_MENU_ITEM_LINK, CompanyListPage.class)));

        if(WebSession.get().isSignedIn() && WebSession.get().getUser().isAdmin()) {
            linkList.addAll(getAdminMenuLinks());
        }

        return linkList;
    }

    private List<MenuItem> getAdminMenuLinks() {
        List<MenuItem> linkList = new ArrayList<MenuItem>();

        linkList.add(new MenuSeparator());
        linkList.add(new MenuLink("label.reference.criterion.overview", new BookmarkablePageLink<ReferenceCriterionListPage>(USER_MENU_ITEM_LINK, ReferenceCriterionListPage.class)));

        return linkList;
    }

    private void createLoggedOutPanel() {
        final BookmarkablePageLink<LoginPage> signin = new BookmarkablePageLink<LoginPage>("signin", LoginPage.class);
        signin.setVisible(!WebSession.get().isSignedIn());
        add(signin);

        final BookmarkablePageLink<RegistrationPage> register = new BookmarkablePageLink<RegistrationPage>("register", RegistrationPage.class);
        register.setVisible(!WebSession.get().isSignedIn());
        add(register);
    }

    private abstract class MenuItem implements Serializable {

    }

    private class MenuLink extends MenuItem {
        private final String label;
        private final BookmarkablePageLink<? extends WebPage> page;

        public MenuLink(String label, BookmarkablePageLink<? extends WebPage> page) {
            this.label = label;
            this.page = page;
        }

        public BookmarkablePageLink<? extends WebPage> getPage() {
            return page;
        }

        public String getLabel() {
            return label;
        }
    }

    private class MenuSeparator extends MenuItem {

    }

    private class MenuTitle extends MenuItem {
        private final String title;

        public MenuTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }
}
