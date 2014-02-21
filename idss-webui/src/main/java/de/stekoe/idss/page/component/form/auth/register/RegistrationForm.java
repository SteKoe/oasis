package de.stekoe.idss.page.component.form.auth.register;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert.Type;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.mail.template.RegistrationMailTemplate;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
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

    @SpringBean private MailService itsMailService;
    @SpringBean private UserService itsUserService;
    @SpringBean private SystemRoleService itsSystemRoleService;
    @SpringBean private AuthService itsAuthService;

    private final User itsUser = new User();

    private Alert itsSuccessMessage;
    private Alert itsErrorMessage;
    private Form<User> itsForm;

    public RegistrationForm(String aId) {
        super(aId);

        createRegistrationForm();
        createSuccessMessage();
        createErrorMessage();
    }

    private void createSuccessMessage() {
        itsSuccessMessage = new Alert("success", new StringResourceModel("message.registration.success", this, null));
        itsSuccessMessage.type(Type.Success);
        itsSuccessMessage.setCloseButtonVisible(true);
        itsSuccessMessage.setVisible(false);
        add(itsSuccessMessage);
    }

    private void createErrorMessage() {
        itsErrorMessage = new Alert("error", new StringResourceModel("message.registration.error", this, null));
        itsErrorMessage.type(Type.Danger);
        itsErrorMessage.setCloseButtonVisible(true);
        itsErrorMessage.setVisible(false);
        add(itsErrorMessage);
    }

    private void createRegistrationForm() {
        itsForm = new Form<User>("registrationForm") {

            @Override
            protected void onValidate() {
                itsErrorMessage.setVisible(false);
                itsSuccessMessage.setVisible(false);
                super.onValidate();
            }

            @Override
            protected void onSubmit() {
                try {
                    User user = createUser();
                    itsUserService.save(user);
                    sendActivationMail(user);
                    itsSuccessMessage.setVisible(true);
                    setVisible(false);
                } catch(UsernameAlreadyInUseException e) {
                    itsErrorMessage.setVisible(true);
                    LOG.error("A user tried to register with existing username!");
                } catch (EmailAddressAlreadyInUseException e) {
                    itsErrorMessage.setVisible(true);
                    LOG.error("A user tried to register with existing email address!");
                }
            }

            private User createUser() {
                User user = getModelObject();
                String hashedPassword = itsAuthService.hashPassword(user.getPassword());
                user.setPassword(hashedPassword);

                // Set standard roles
                SystemRole userRole = itsSystemRoleService.getUserRole();
                user.getRoles().add(userRole);

                // Create a user profile
                UserProfile userProfile = new UserProfile();
                user.setProfile(userProfile);

                return user;
            }

            private void sendActivationMail(User aUser) {
                String address = aUser.getEmail();
                String subject = "Successfully registered!";

                Map<String, String> variables = new HashMap<String, String>();
                variables.put("username", aUser.getUsername());
                variables.put("activationlink", createActivationLink(aUser));

                String message = new RegistrationMailTemplate().setVariables(variables);

                itsMailService.sendMail(address, subject, message);
            }
        };
        add(itsForm);

        RequiredTextField usernameField = new RequiredTextField<String>("username");
        usernameField.add(new UniqueValueValidator(itsUserService.getAllUsernames()));
        itsForm.add(usernameField);

        EmailTextField email = new EmailTextField("email");
        email.setRequired(true);
        email.add(new UniqueValueValidator(itsUserService.getAllEmailAddresses()));
        itsForm.add(email);

        PasswordTextField password = new PasswordTextField("password");
        password.setLabel(Model.of(getString("label.password")));
        itsForm.add(password);

        PasswordTextField passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
        itsForm.add(passwordConfirm);

        Button submitButton = new Button("submit");
        submitButton.setModel(Model.of(getString("label.submit")));
        itsForm.add(submitButton);

        itsForm.setModel(new CompoundPropertyModel<User>(itsUser));
        itsForm.add(getPasswordsEqualBehavior());
    }

    private String createActivationLink(User aUser) {
        PageParameters pp = new PageParameters();
        pp.set(0, aUser.getActivationKey());

        return RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlFor(ActivateUserPage.class, pp)));
    }

    private EqualPasswordInputValidator getPasswordsEqualBehavior() {
        return new EqualPasswordInputValidator((FormComponent) itsForm.get("password"), (FormComponent) itsForm.get("passwordConfirm"));
    }
}