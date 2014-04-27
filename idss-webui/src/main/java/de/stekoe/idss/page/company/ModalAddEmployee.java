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

package de.stekoe.idss.page.company;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;

public abstract class ModalAddEmployee extends Modal {

    @SpringBean
    private UserService userService;

    private final ProjectMember projectMember = new ProjectMember();

    private String user;

    public ModalAddEmployee(String id) {
        super(id);

        header(Model.of(getString("label.project.add.member")));

        final Form form = new Form("form.add.member") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                User existingUser = userService.findByUsernameOrEmail(user);
                onSave(existingUser);
                user = "";
            }
        };
        add(form);

        TextField<String> usernameOrEmail = new TextField<String>("user", new PropertyModel<String>(this, "user"));
        form.add(usernameOrEmail);

        addButton(new ModalCloseButton(new ResourceModel("label.cancel")));

        final SubmitLink submitButton = new SubmitLink("button", form);
        addButton(submitButton);
        submitButton.setBody(Model.of(getString("label.add")));
        submitButton.add(new ButtonBehavior(Buttons.Type.Success));
    }

    public abstract void onSave(User existingUser);
}
