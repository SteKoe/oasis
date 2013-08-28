package de.stekoe.idss.page.auth.user;

import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.mindrot.jbcrypt.BCrypt;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;

/**
 * Page for changing password and/or email address.
 */
@SuppressWarnings("serial")
public class EditPasswordPage extends AuthUserPage {

    @SpringBean
    private IUserService userService;

    private String currentPassword;
    private String newPassword;
    private String newPasswordConfirm;
    private String newEmail;
    private String newEmailConfirm;
    private PasswordTextField currentPasswordField;
    private PasswordTextField newPasswordField;
    private PasswordTextField newPasswordConfirmField;
    private EmailTextField newEmailField;
    private EmailTextField newEmailConfirmField;

    /**
     * Construct.
     */
    public EditPasswordPage() {
        @SuppressWarnings("rawtypes")
        Form form = new Form("editPassword") {

            @Override
            protected void onSubmit() {
                User user = ((IDSSSession) getSession()).getUser();
                if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
                    error(getString("currentPasswort.wrong"));
                    return;
                }

                if (newPassword != null && newPasswordConfirm != null) {
                    user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                    info(getString("passwordChanged.success"));
                }

                if (newEmail != null && newEmailConfirm != null) {
                    user.setEmail(newEmail);
                    info(getString("emailChanged.success"));
                }

                if (userService != null) {
                    userService.save(user);
                }
            }
        };

        // Current password
        form.add(createCurrentPasswordGroup());
        // Password
        form.add(createNewPasswordGroup());
        form.add(createNewPasswordConfirmGroup());
        // Email
        form.add(createNewEmailGroup());
        form.add(createNewEmailConfirmGroup());

        form.add(new EqualInputValidator(newPasswordField, newPasswordConfirmField));
        form.add(new EqualInputValidator(newEmailField, newEmailConfirmField));

        add(form);
    }

    private ControlGroup createCurrentPasswordGroup() {
        currentPasswordField = new PasswordTextField("currentPassword", new PropertyModel(this, "currentPassword"));
        currentPasswordField.setRequired(true);
        currentPasswordField.setLabel(new StringResourceModel("currentPassword.label", this, null));

        ControlGroup group = new ControlGroup("currentPasswordControlGroup");
        group.add(currentPasswordField);
        return group;
    }

    private ControlGroup createNewPasswordGroup() {
        newPasswordField = new PasswordTextField("newPassword", new PropertyModel(this, "newPassword"));
        newPasswordField.setRequired(false);
        newPasswordField.setLabel(new StringResourceModel("newPassword.label", this, null));

        ControlGroup group = new ControlGroup("newPasswordControlGroup");
        group.add(newPasswordField);
        return group;
    }

    private ControlGroup createNewPasswordConfirmGroup() {
        newPasswordConfirmField = new PasswordTextField("newPasswordConfirm", new PropertyModel(this, "newPasswordConfirm"));
        newPasswordConfirmField.setRequired(false);
        newPasswordConfirmField.setLabel(new StringResourceModel("newPasswordConfirm.label", this, null));

        ControlGroup group = new ControlGroup("newPasswordConfirmControlGroup");
        group.add(newPasswordConfirmField);
        return group;
    }

    private ControlGroup createNewEmailGroup() {
        newEmailField = new EmailTextField("newEmail", new PropertyModel(this, "newEmail"));
        newEmailField.setRequired(false);
        newEmailField.setLabel(new StringResourceModel("newEmail.label", this, null));

        ControlGroup group = new ControlGroup("newEmailControlGroup");
        group.add(newEmailField);
        return group;
    }

    private ControlGroup createNewEmailConfirmGroup() {
        newEmailConfirmField = new EmailTextField("newEmailConfirm", new PropertyModel(this, "newEmailConfirm"));
        newEmailConfirmField.setRequired(false);
        newEmailConfirmField.setLabel(new StringResourceModel("newEmailConfirm.label", this, null));

        ControlGroup group = new ControlGroup("newEmailConfirmControlGroup");
        group.add(newEmailConfirmField);
        return group;
    }
}
