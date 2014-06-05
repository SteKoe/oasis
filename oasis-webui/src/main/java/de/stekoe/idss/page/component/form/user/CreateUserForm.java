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

package de.stekoe.idss.page.component.form.user;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.SystemRoleService;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger 
 */
public class CreateUserForm extends Panel {

    @SpringBean
    SystemRoleService systemRoleService;

    final IModel<User> userModel = Model.of(new User());

    public CreateUserForm(java.lang.String id) {
        super(id);

        final Form<User> form = new Form<User>("createUserForm") {

        };
        add(form);

        final FormGroup usernameFormGroup = new FormGroup("usernameFormGroup");
        form.add(usernameFormGroup);
        final TextField<java.lang.String> username = new TextField<java.lang.String>("username", new PropertyModel<java.lang.String>(userModel, "username"));
        usernameFormGroup.add(username);
        username.setLabel(Model.of(getString("label.username")));

        final FormGroup emailFormGroup = new FormGroup("emailFormGroup");
        form.add(emailFormGroup);
        final EmailTextField email = new EmailTextField("email", new PropertyModel<java.lang.String>(userModel, "email"));
        emailFormGroup.add(email);
        email.setLabel(Model.of(getString("label.email")));

        final FormGroup passwordFormGroup = new FormGroup("passwordFormGroup");
        form.add(passwordFormGroup);
        final PasswordTextField password = new PasswordTextField("password", new PropertyModel<java.lang.String>(userModel, "password"));
        passwordFormGroup.add(password);
        password.setLabel(Model.of(getString("label.password")));

        final FormGroup systemRoleFormGroup = new FormGroup("systemRoleFormGroup");
        form.add(systemRoleFormGroup);
        final ListMultipleChoice listMultipleChoice = new ListMultipleChoice("systemRole", new PropertyModel(userModel, "roles"), systemRoleService.findAll());
        systemRoleFormGroup.add(listMultipleChoice);

        final FormGroup buttonFormGroup = new FormGroup("buttonFormGroup");
        form.add(buttonFormGroup);
        final Button submit = new Button("submit");
        buttonFormGroup.add(submit);
    }
}
