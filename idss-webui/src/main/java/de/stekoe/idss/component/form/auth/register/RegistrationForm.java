package de.stekoe.idss.component.form.auth.register;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.mail.template.RegistrationMailTemplate;
import de.stekoe.idss.model.*;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.MailService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.validator.UniqueValueValidator;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class RegistrationForm extends Panel {
    private static final Logger LOG = Logger.getLogger(RegistrationForm.class);

    @SpringBean private MailService mailService;
    @SpringBean private UserService userService;
    @SpringBean private SystemRoleService systemRoleService;
    @SpringBean private AuthService authService;

    private final User user = new User();

    private Form<User> form;

    private RequiredTextField<java.lang.String> usernameField;
    private EmailTextField email;
    private PasswordTextField password;
    private PasswordTextField passwordConfirm;
    private Button submitButton;

    private Alert successMessage;
    private Alert errorMessage;

    public RegistrationForm(java.lang.String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        createRegistrationForm();
        createSuccessMessage();
        createErrorMessage();
    }

    private void createSuccessMessage() {
        successMessage = new Alert("success", new StringResourceModel("message.registration.success", this, null));
        successMessage.type(Type.Success);
        successMessage.setCloseButtonVisible(true);
        successMessage.setVisible(false);
        add(successMessage);
    }

    private void createErrorMessage() {
        errorMessage = new Alert("error", new StringResourceModel("message.registration.error", this, null));
        errorMessage.type(Type.Error);
        errorMessage.setCloseButtonVisible(true);
        errorMessage.setVisible(false);
        add(errorMessage);
    }

    private void createRegistrationForm() {
        createFields();

        form = new Form<User>("registrationForm") {

            @Override
            protected void onValidate() {
                errorMessage.setVisible(false);
                successMessage.setVisible(false);
                super.onValidate();
            }

            @Override
            protected void onSubmit() {
                try {
                    User user = createUser();
                    userService.save(user);
                    sendActivationMail(user);
                    successMessage.setVisible(true);
                    form.setVisible(false);
                } catch(UsernameAlreadyInUseException e) {
                    errorMessage.setVisible(true);
                    LOG.error("A user tried to register with existing username!");
                } catch (EmailAddressAlreadyInUseException e) {
                    errorMessage.setVisible(true);
                    LOG.error("A user tried to register with existing email address!");
                }
            }

            private User createUser() {
                User user = getModelObject();
                java.lang.String hashedPassword = authService.hashPassword(user.getPassword());
                user.setPassword(hashedPassword);

                // Set standard roles
                SystemRole userRole = systemRoleService.getUserRole();
                user.getRoles().add(userRole);

                // Create a user profile
                UserProfile userProfile = new UserProfile();
                user.setProfile(userProfile);

                return user;
            }

            private void sendActivationMail(User user) {
                java.lang.String address = user.getEmail();
                java.lang.String subject = "Successfully registered!";

                Map<java.lang.String, java.lang.String> variables = new HashMap<java.lang.String, java.lang.String>();
                variables.put("username", user.getUsername());
                variables.put("activationlink", createActivationLink(user));

                java.lang.String message = new RegistrationMailTemplate().setVariables(variables);

                mailService.sendMail(address, subject, message);
            }
        };
        form.setModel(new CompoundPropertyModel<User>(user));

        form.add(getUsernameControlGroup());
        form.add(getEmailControlGroup());
        form.add(getPasswordControlGroup());
        form.add(getPasswordConfirmControlGroup());
        form.add(getButtonControlGroup());

        form.add(getPasswordsEqualBehavior());

        add(form);
    }

    private void createFields() {
        setUsernameField();
        setEmailField();
        setPasswordField();
        setPasswordConfirmField();
        setSubmitButton();
    }

    private java.lang.String createActivationLink(User user) {
        PageParameters pp = new PageParameters();
        pp.set(0, user.getActivationKey());

        return RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlFor(ActivateUserPage.class, pp)));
    }

    private ControlGroup getUsernameControlGroup() {
        java.lang.String id = "usernameControlGroup";
        RequiredTextField<java.lang.String> field = getUsernameField();
        return createControlGroup(id, field);
    }

    private void setUsernameField() {
        usernameField = new RequiredTextField<java.lang.String>("username");
        usernameField.setLabel(Model.of(getString("label.username")));
        usernameField.add(new UniqueValueValidator(userService.getAllUsernames()));
    }

    private RequiredTextField<java.lang.String> getUsernameField() {
        return usernameField;
    }

    private ControlGroup getEmailControlGroup() {
        java.lang.String id = "emailControlGroup";
        EmailTextField field = getEmailField();
        return createControlGroup(id, field);
    }

    private void setEmailField() {
        email = new EmailTextField("email");
        email.setRequired(true);
        email.setLabel(Model.of(getString("label.email")));
        email.add(new UniqueValueValidator(userService.getAllEmailAddresses()));
    }

    private EmailTextField getEmailField() {
        return email;
    }

    private ControlGroup getPasswordControlGroup() {
        java.lang.String id = "passwordControlGroup";
        PasswordTextField field = getPasswordField();
        return createControlGroup(id, field);
    }

    private PasswordTextField getPasswordField() {
        return password;
    }

    private void setPasswordField() {
        password = new PasswordTextField("password");
        password.setLabel(Model.of(getString("label.password")));
    }

    private ControlGroup getPasswordConfirmControlGroup() {
        java.lang.String id = "passwordConfirmControlGroup";
        PasswordTextField field = getPasswordConfirmField();
        return createControlGroup(id, field);
    }

    private PasswordTextField getPasswordConfirmField() {
        return passwordConfirm;
    }

    private void setPasswordConfirmField() {
        passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
        passwordConfirm.setLabel(Model.of(getString("label.password.confirm")));
    }

    private ControlGroup getButtonControlGroup() {
        java.lang.String id = "buttonControlGroup";
        Button submitButton = getSubmitButton();
        return createControlGroup(id, submitButton);
    }

    private Button getSubmitButton() {
        return submitButton;
    }

    private void setSubmitButton() {
        submitButton = new Button("submit");
        submitButton.setModel(Model.of(getString("label.submit")));
    }

    @SuppressWarnings("rawtypes")
    private ControlGroup createControlGroup(java.lang.String id, FormComponent... field) {
        ControlGroup cg = new ControlGroup(id);
        cg.add(field);
        return cg;
    }

    private EqualPasswordInputValidator getPasswordsEqualBehavior() {
        return new EqualPasswordInputValidator(getPasswordField(),
                getPasswordConfirmField());
    }
}