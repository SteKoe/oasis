package de.stekoe.idss.page.auth.user;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserProfilePage extends AuthUserPage {

    /**
     * Construct.
     */
    public UserProfilePage() {
//        User user = getSession().getUser();
        add(new Label("username", Model.of("test")));
    }
}
