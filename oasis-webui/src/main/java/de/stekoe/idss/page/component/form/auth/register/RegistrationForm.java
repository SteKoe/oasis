package de.stekoe.idss.page.component.form.auth.register;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Alert.Type;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.mail.template.RegistrationMailTemplate;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.EmailAddressAlreadyInUseException;
import de.stekoe.idss.service.MailService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.service.UsernameAlreadyInUseException;
import de.stekoe.idss.validator.UniqueValueValidator;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

/**
 * @author Stephan Koeninger
 */
@SuppressWarnings("serial")
public class RegistrationForm extends Panel {
    private static final Logger LOG = Logger.getLogger(RegistrationForm.class);

    @SpringBean
    private MailService itsMailService;
    @SpringBean
    private UserService itsUserService;
    @SpringBean
    private SystemRoleService itsSystemRoleService;
    @SpringBean
    private AuthService itsAuthService;

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
                } catch (UserException e) {
                    if (e instanceof UsernameAlreadyInUseException) {
                        itsErrorMessage.setVisible(true);
                        LOG.error("A user tried to register with existing username!", e);
                    } else if (e instanceof EmailAddressAlreadyInUseException) {
                        itsErrorMessage.setVisible(true);
                        LOG.error("A user tried to register with existing email address!", e);
                    }
                }
                setResponsePage(HomePage.class);
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
        itsForm.setModel(new CompoundPropertyModel<User>(itsUser));

        RequiredTextField usernameField = new RequiredTextField<String>("username");
        itsForm.add(new FormGroup("group.username").add(usernameField));
        usernameField.setLabel(Model.of(getString("label.username")));
        usernameField.add(new UniqueValueValidator(itsUserService.findAllUsernames()));

        EmailTextField email = new EmailTextField("email");
        itsForm.add(new FormGroup("group.email").add(email));
        email.setLabel(Model.of(getString("label.email")));
        email.add(new UniqueValueValidator(itsUserService.findAllEmailAddresses()));
        email.setRequired(true);

        PasswordTextField password = new PasswordTextField("password");
        itsForm.add(new FormGroup("group.password").add(password));
        password.setLabel(Model.of(getString("label.password")));

        PasswordTextField passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
        itsForm.add(new FormGroup("group.passwordConfirm").add(passwordConfirm));
        passwordConfirm.setLabel(Model.of(getString("label.password.confirm")));

        Button submitButton = new Button("submit");
        itsForm.add(submitButton);

        itsForm.add(getPasswordsEqualBehavior());
        itsForm.add(new MarkRequiredFieldsBehavior());
    }

    private String createActivationLink(User aUser) {
        PageParameters pp = new PageParameters();
        pp.set(0, aUser.getActivationKey());

        return RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlFor(ActivateUserPage.class, pp)));
    }

    private EqualPasswordInputValidator getPasswordsEqualBehavior() {
        final FormComponent formComponent1 = (FormComponent) itsForm.get("group.password:group.password_body:password");
        final FormComponent formComponent2 = (FormComponent) itsForm.get("group.passwordConfirm:group.passwordConfirm_body:passwordConfirm");
        return new EqualPasswordInputValidator(formComponent1, formComponent2);
    }
}