package de.stekoe.idss;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.auth.user.LogoutPage;
import de.stekoe.idss.page.auth.user.UserProfilePage;

@SuppressWarnings("serial")
public class UserPanel extends Panel {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public UserPanel(String id) {
        super(id);
        Link userProfileLink = new Link("userProfile") {
            @Override
            public void onClick() {
                throw new RestartResponseAtInterceptPageException(UserProfilePage.class);
            }
        };
        userProfileLink.add(new Label("username", new PropertyModel(this, "session.user.username")));
        add(userProfileLink);
        PageParameters parameters = new PageParameters();
//      parameters.add(SignOutPage.REDIRECTPAGE_PARAM, logoutPageClass.getName());
        add(new BookmarkablePageLink("signout", LogoutPage.class, parameters) {
            @Override
            public boolean isVisible() {
                return IDSSSession.get().isLoggedIn();
            }
        });
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

}
