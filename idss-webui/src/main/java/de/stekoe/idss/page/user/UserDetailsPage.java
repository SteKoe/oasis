package de.stekoe.idss.page.user;

import de.bripkens.gravatar.DefaultImage;
import de.bripkens.gravatar.Gravatar;
import de.bripkens.gravatar.Rating;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.IUserService;
import de.stekoe.idss.wicket.ExternalImage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class UserDetailsPage extends AuthUserPage {

    @Inject
    private IUserService userService;
    private User user;

    public UserDetailsPage(PageParameters params) {
        final StringValue paramUserId = params.get("id");
        final String userId = paramUserId.toString();

        if(userId == null) {
            return;
        }

        user = userService.findById(userId);

        avatar();

        add(new Label("username", user.getUsername()));
    }

    private void avatar() {
        Gravatar gravatar = new Gravatar();
        gravatar.setRating(Rating.GENERAL_AUDIENCE);
        gravatar.setHttps(true);
        gravatar.setSize(512);
        gravatar.setStandardDefaultImage(DefaultImage.MYSTERY_MAN);

        final String url = gravatar.getUrl(getSession().getUser().getEmail());
        final ExternalImage avatar = new ExternalImage("avatar", url);

        add(avatar);
    }
}
