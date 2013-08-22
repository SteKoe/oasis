package de.stekoe.idss.panel;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.auth.admin.EditUserPage;
import de.stekoe.idss.page.auth.user.LogoutPage;
import de.stekoe.idss.page.auth.user.UserProfilePage;

@SuppressWarnings("serial")
public class UserPanel extends Panel {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UserPanel(String id) {
        super(id);
        createLoggedInPanel();
        createLoggedOutPanel();
        createAdminPanel();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createLoggedInPanel() {
        Link userProfileLink = new Link("userProfile") {
            @Override
            public void onClick() {
                throw new RestartResponseAtInterceptPageException(UserProfilePage.class);
            }
        };
        userProfileLink.add(new Label("username", new PropertyModel(this, "session.user.username")));
        add(userProfileLink);
        add(new BookmarkablePageLink("signout", LogoutPage.class) {
            @Override
            public boolean isVisible() {
                return IDSSSession.get().isLoggedIn();
            }
        });
    }

    @SuppressWarnings("rawtypes")
    private void createLoggedOutPanel() {
        add(new Link("signin") {
            @Override
            public void onClick() {
                throw new RestartResponseAtInterceptPageException(LoginPage.class);
            }

            @Override
            public boolean isVisible() {
                return !IDSSSession.get().isLoggedIn();
            }
        });
        add(new Link("register") {
            @Override
            public void onClick() {
                throw new RestartResponseAtInterceptPageException(RegistrationPage.class);
            }

            @Override
            public boolean isVisible() {
                return !IDSSSession.get().isLoggedIn();
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createAdminPanel() {
        add(new BookmarkablePageLink("editUsers", EditUserPage.class) {
            @Override
            public boolean isVisible() {
                User user = IDSSSession.get().getUser();
                if(user == null)
                    return false;
                return user.isAdmin();
            }
        });
    }

}