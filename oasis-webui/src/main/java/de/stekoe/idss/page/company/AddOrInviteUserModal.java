package de.stekoe.idss.page.company;

import javax.inject.Inject;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;

public abstract class AddOrInviteUserModal extends Modal {

    @Inject
    private UserService userService;

    private String usernameOrEmail = "";

    public AddOrInviteUserModal(String wicketId) {
        super(wicketId);

        final Form form = new Form("form.add.member") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                User existingUser = userService.findByUsernameOrEmail(usernameOrEmail);
                if(existingUser != null) {
                    foundExistingUser(existingUser);
                } else {
                    inviteUser(usernameOrEmail);
                }
                usernameOrEmail = "";
            }
        };
        add(form);

        TextField<String> usernameOrEmail = new TextField<String>("user", new PropertyModel<String>(this, "usernameOrEmail"));
        form.add(usernameOrEmail);

        addButton(new ModalCloseButton(new ResourceModel("label.cancel")));

        final SubmitLink submitButton = new SubmitLink("button", form);
        addButton(submitButton);
        submitButton.setBody(Model.of(getString("label.add")));
        submitButton.add(new ButtonBehavior(Buttons.Type.Success));
    }

    public abstract void foundExistingUser(User user);
    public abstract void inviteUser(String usernameOrEmail);
}
