package de.stekoe.idss.page.user;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualInputValidator;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.session.WebSession;

/**
 * Page for changing password and/or email address.
 */
@SuppressWarnings("serial")
public class EditPasswordPage extends AuthUserPage {

    private static final Logger LOG = Logger.getLogger(EditPasswordPage.class);

    @SpringBean
    private UserService userService;
    @SpringBean
    private AuthService authService;

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

    public EditPasswordPage() {
        setTitle(getString("label.changemailorpassword"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        @SuppressWarnings("rawtypes")
        Form form = new Form("editPassword") {

            @Override
            protected void onSubmit() {
                User user = ((WebSession) getSession()).getUser();
                if (!authService.checkPassword(currentPassword, user.getPassword())) {
                    getSession().error(getString("currentPasswort.wrong"));
                    return;
                }

                if (newPassword != null && newPasswordConfirm != null) {
                    user.setPassword(authService.hashPassword(newPassword));
                    getSession().info(getString("passwordChanged.success"));
                }

                if (newEmail != null && newEmailConfirm != null) {
                    user.setEmail(newEmail);
                    getSession().info(getString("emailChanged.success"));
                }

                try {
                    userService.save(user);
                } catch (UserException e) {
                    String username = user.getUsername();
                    String email = user.getEmail();
                    String errorMsg = String.format("User %s tried to change email address to %s. But this address is already registered.", username, email);
                    LOG.error(errorMsg, e);
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

    private FormGroup createCurrentPasswordGroup() {
        currentPasswordField = new PasswordTextField("currentPassword", new PropertyModel(this, "currentPassword"));
        currentPasswordField.setRequired(true);

        FormGroup group = new FormGroup("currentPasswordFormGroup");
        group.add(currentPasswordField);
        return group;
    }

    private FormGroup createNewPasswordGroup() {
        newPasswordField = new PasswordTextField("newPassword", new PropertyModel(this, "newPassword"));
        newPasswordField.setRequired(false);

        FormGroup group = new FormGroup("newPasswordFormGroup");
        group.add(newPasswordField);
        return group;
    }

    private FormGroup createNewPasswordConfirmGroup() {
        newPasswordConfirmField = new PasswordTextField("newPasswordConfirm", new PropertyModel(this, "newPasswordConfirm"));
        newPasswordConfirmField.setRequired(false);

        FormGroup group = new FormGroup("newPasswordConfirmFormGroup");
        group.add(newPasswordConfirmField);
        return group;
    }

    private FormGroup createNewEmailGroup() {
        newEmailField = new EmailTextField("newEmail", new PropertyModel(this, "newEmail"));
        newEmailField.setRequired(false);

        FormGroup group = new FormGroup("newEmailFormGroup");
        group.add(newEmailField);
        return group;
    }

    private FormGroup createNewEmailConfirmGroup() {
        newEmailConfirmField = new EmailTextField("newEmailConfirm", new PropertyModel(this, "newEmailConfirm"));
        newEmailConfirmField.setRequired(false);

        FormGroup group = new FormGroup("newEmailConfirmFormGroup");
        group.add(newEmailConfirmField);
        return group;
    }
}
