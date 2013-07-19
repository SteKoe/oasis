package de.stekoe.idss.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class UserProfilePage extends AuthUserPage {

    public UserProfilePage() {
        User user = getSession().getUser();
        add(new Label("username", Model.of(user.getUsername())));
    }
}
