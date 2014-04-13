package de.stekoe.idss.page.component.form.auth.login;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.WebApplication;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.component.behavior.Placeholder;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;

public class ResetPasswordPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(ResetPasswordPage.class);

    @Inject
    UserService userService;

    @Inject
    AuthService authService;

    private String password;
    private final UserModel userModel;
    private final Form form;

    public ResetPasswordPage(PageParameters params) {
        StringValue resetToken = params.get("token");
        userModel = new UserModel(resetToken.toString());
        if(userModel.getObject() == null) {
            redirectOnFailure();
        }

        form = new Form("resetPasswordForm") {
            @Override
            protected void onSubmit() {
                if(!StringUtils.isBlank(password)) {
                    User user = userModel.getObject();
                    user.setPassword(authService.hashPassword(password));
                    user.setActivationKey(null);
                    user.setUserStatus(UserStatus.ACTIVATED);

                    try {
                        userService.save(user);
                        success(getString("message.password.reset.success"));
                        setResponsePage(WebApplication.get().getHomePage());
                        return;
                    } catch (UserException e) {
                        LOG.error("Error saving user.", e);
                    }
                }

                error(getString("message.password.reset.error"));
                setResponsePage(getPage());
                return;
            }
        };
        add(form);

        PasswordTextField password = new PasswordTextField("password", new PropertyModel<String>(this, "password"));
        password.add(new Placeholder(getString("label.password")));
        form.add(new FormGroup("group.password").add(password));
        password.setLabel(Model.of(getString("label.password")));

        PasswordTextField passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
        passwordConfirm.add(new Placeholder(getString("label.password.confirm")));
        form.add(new FormGroup("group.passwordConfirm").add(passwordConfirm));
        passwordConfirm.setLabel(Model.of(getString("label.password.confirm")));


        form.add(getPasswordsEqualBehavior());
    }

    private EqualPasswordInputValidator getPasswordsEqualBehavior() {
        final FormComponent formComponent1 = (FormComponent) form.get("group.password:group.password_body:password");
        final FormComponent formComponent2 = (FormComponent) form.get("group.passwordConfirm:group.passwordConfirm_body:passwordConfirm");
        return new EqualPasswordInputValidator(formComponent1, formComponent2);
    }

    private void redirectOnFailure() {
        error(getString("message.password.reset.error"));
        setResponsePage(HomePage.class);
    }

    private class UserModel extends Model<User> {
        private final String resetPasswordToken;
        private User user;

        public UserModel(String resetPasswordToken) {
            this.resetPasswordToken = resetPasswordToken;
        }

        @Override
        public User getObject() {
            if(StringUtils.isBlank(resetPasswordToken)) {
                return null;
            }

            if(user == null) {
                user = userService.findByPasswordResetToken(resetPasswordToken);
                return user;
            } else {
                return user;
            }
        }

        @Override
        public void detach() {
            this.user = null;
        }
    }
}
