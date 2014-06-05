/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.component.user;

import de.bripkens.gravatar.Gravatar;
import de.stekoe.idss.model.User;
import de.stekoe.idss.wicket.ExternalImage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/**
 * @author Stephan Koeninger 
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
