package de.stekoe.idss.component.form.user;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.SystemRoleService;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateUserForm extends Panel {

    @SpringBean
    SystemRoleService systemRoleService;

    final IModel<String> userModel = Model.of(new String());

    public CreateUserForm(java.lang.String id) {
        super(id);

        final Form<User> form = new Form<User>("createUserForm") {

        };
        add(form);

        final ControlGroup usernameControlGroup = new ControlGroup("usernameControlGroup");
        form.add(usernameControlGroup);
        final TextField<java.lang.String> username = new TextField<java.lang.String>("username", new PropertyModel<java.lang.String>(userModel, "username"));
        usernameControlGroup.add(username);
        username.setLabel(Model.of(getString("label.username")));

        final ControlGroup emailControlGroup = new ControlGroup("emailControlGroup");
        form.add(emailControlGroup);
        final EmailTextField email = new EmailTextField("email", new PropertyModel<java.lang.String>(userModel, "email"));
        emailControlGroup.add(email);
        email.setLabel(Model.of(getString("label.email")));

        final ControlGroup passwordControlGroup = new ControlGroup("passwordControlGroup");
        form.add(passwordControlGroup);
        final PasswordTextField password = new PasswordTextField("password", new PropertyModel<java.lang.String>(userModel, "password"));
        passwordControlGroup.add(password);
        password.setLabel(Model.of(getString("label.password")));

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
