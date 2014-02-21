package de.stekoe.idss.page.component.user;

import de.bripkens.gravatar.Gravatar;
import de.stekoe.idss.model.User;
import de.stekoe.idss.wicket.ExternalImage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class UserInfoBlock extends Panel {
    private final User user;
    private String info = "Projektleiter";

    public UserInfoBlock(String id, User user) {
        this(id, user, null);
    }

    public UserInfoBlock(String id, User user, String info) {
        super(id);

        this.user = user;
        this.info = info;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final Gravatar gravatar = new Gravatar();
        gravatar.setSize(40);
        final String url = gravatar.getUrl(user.getEmail());

        add(new ExternalImage("user.avatar", url));

        add(new Label("user.info", info));
        add(new Label("user.username", new PropertyModel<String>(user, "username")));
        add(new Label("user.email", new PropertyModel<String>(user, "email")));
    }
}
