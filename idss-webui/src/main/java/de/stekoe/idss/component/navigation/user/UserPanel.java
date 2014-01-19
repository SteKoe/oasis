package de.stekoe.idss.component.navigation.user;

import de.stekoe.idss.model.User;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.page.auth.LogoutPage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.user.CreateUserPage;
import de.stekoe.idss.page.user.EditUserProfilePage;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel shown when user is logged in.
 */
@SuppressWarnings("serial")
public class UserPanel extends Panel {

    private java.lang.String username = "N/A";

    /**
     * Construct.
     * @param id wicket:id
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UserPanel(java.lang.String id) {
        super(id);

        final User currentUser = WebSession.get().getUser();
        if(currentUser != null) {
            username = currentUser.getUsername();
        }

        createLoggedInPanel();
        createLoggedOutPanel();
        createAdminPanel();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
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
    }

    private void createLoggedOutPanel() {
        final BookmarkablePageLink<LoginPage> signin = new BookmarkablePageLink<LoginPage>("signin", LoginPage.class);
        signin.setVisible(!WebSession.get().isSignedIn());
        add(signin);

        final BookmarkablePageLink<RegistrationPage> register = new BookmarkablePageLink<RegistrationPage>("register", RegistrationPage.class);
        register.setVisible(!WebSession.get().isSignedIn());
        add(register);
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