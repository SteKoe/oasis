package de.stekoe.idss.component.navigation.user;

import de.stekoe.idss.model.User;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.page.auth.LogoutPage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.user.CreateUserPage;
import de.stekoe.idss.page.user.UserProfilePage;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * Panel shown when user is logged in.
 */
@SuppressWarnings("serial")
public class UserPanel extends Panel {

    /**
     * Construct.
     * @param id wicket:id
     */
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
                return WebSession.get().isSignedIn();
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
                return !WebSession.get().isSignedIn();
            }
        });
        add(new Link("register") {
            @Override
            public void onClick() {
                throw new RestartResponseAtInterceptPageException(RegistrationPage.class);
            }

            @Override
            public boolean isVisible() {
                return !WebSession.get().isSignedIn();
            }
        });
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void createAdminPanel() {
        add(new BookmarkablePageLink("editUsers", CreateUserPage.class) {
            @Override
            public boolean isVisible() {
                User user = WebSession.get().getUser();
                if (user == null) {
                    return false;
                }
                return user.isAdmin();
            }
        });
    }

}
