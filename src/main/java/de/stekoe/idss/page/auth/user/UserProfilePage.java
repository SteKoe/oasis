package de.stekoe.idss.page.auth.user;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserProfilePage extends AuthUserPage {

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

    }

    /**
     * Construct.
     */
    public UserProfilePage() {
        User user = getSession().getUser();
        add(new Label("username", Model.of(user.getUsername())));
        add(new Link("changePasswordOrEmailLink") {
            @Override
            public void onClick() {
            }
        });
        add(createDateTextField());
    }

    private DateTextField createDateTextField() {
        DateTextFieldConfig config = new DateTextFieldConfig();
        config.autoClose(true);
        config.withLanguage(IDSSSession.get().getLocale().getLanguage());
        config.showTodayButton(true);
        return new DateTextField("inputBirthday", config);
    }
}
