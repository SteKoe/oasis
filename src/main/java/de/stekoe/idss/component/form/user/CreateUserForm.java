package de.stekoe.idss.component.form.user;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.ISystemRoleService;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateUserForm extends Panel {

    @Inject
    ISystemRoleService systemRoleService;

    final IModel<User> userModel = Model.of(new User());

    public CreateUserForm(String id) {
        super(id);

        final Form<User> form = new Form<User>("createUserForm") {

        };
        add(form);

        final ControlGroup usernameControlGroup = new ControlGroup("usernameControlGroup");
        form.add(usernameControlGroup);
        final TextField<String> username = new TextField<String>("username", new PropertyModel<String>(userModel, "username"));
        usernameControlGroup.add(username);
        username.setLabel(Model.of(getString("form.label.username")));

        final ControlGroup emailControlGroup = new ControlGroup("emailControlGroup");
        form.add(emailControlGroup);
        final EmailTextField email = new EmailTextField("email", new PropertyModel<String>(userModel, "email"));
        emailControlGroup.add(email);
        email.setLabel(Model.of(getString("form.label.email")));

        final ControlGroup passwordControlGroup = new ControlGroup("passwordControlGroup");
        form.add(passwordControlGroup);
        final PasswordTextField password = new PasswordTextField("password", new PropertyModel<String>(userModel, "password"));
        passwordControlGroup.add(password);
        password.setLabel(Model.of(getString("form.label.password")));

        final ControlGroup systemRoleControlGroup = new ControlGroup("systemRoleControlGroup");
        form.add(systemRoleControlGroup);
        final ListMultipleChoice listMultipleChoice = new ListMultipleChoice("systemRole", new PropertyModel(userModel, "roles"), systemRoleService.findAllRoles());
        systemRoleControlGroup.add(listMultipleChoice);

        final ControlGroup buttonControlGroup = new ControlGroup("buttonControlGroup");
        form.add(buttonControlGroup);
        final Button submit = new Button("submit");
        buttonControlGroup.add(submit);
    }
}
