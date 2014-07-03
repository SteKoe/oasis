package de.stekoe.idss.page.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.bripkens.gravatar.Gravatar;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.ExternalImage;

public class ViewUserProfilePage extends AuthUserPage {

    @SpringBean
    UserService userService;

    private User user;

    public ViewUserProfilePage(PageParameters params) {
        super(params);

        final String userId = params.get("id").toString();
        if (userId != null) {
            user = userService.findOne(userId);
        } else {
            user = WebSession.get().getUser();
        }

        if(user == null) {
            setResponsePage(HomePage.class);
            return;
        }

        final java.lang.String gravatarUrl = getGravatar();

        add(new Label("username", user.getUsername()));
        add(new ExternalImage("avatar", gravatarUrl));
    }

    private java.lang.String getGravatar() {
        if (user.getEmail() == null) {
            return "";
        } else {
            Gravatar g = new Gravatar();
            return g.getUrl(user.getEmail());
        }
    }
}
