package de.stekoe.idss.component.form.registration;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.markup.html.basic.Label;
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
import org.mindrot.jbcrypt.BCrypt;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.behavior.Placeholder;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.mail.template.RegistrationMailTemplate;
import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.ActivateUserPage;
import de.stekoe.idss.service.MailService;
import de.stekoe.idss.service.UserManager;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class RegistrationForm extends Panel {

    @SpringBean
    private MailService mailService;

    @SpringBean
    private UserManager userManager;

    private final User user = new User();

    private Form<User> form;

    private RequiredTextField<String> usernameField;
    private EmailTextField email;
    private PasswordTextField password;
    private PasswordTextField passwordConfirm;
    private Button submitButton;

    private Label successMessage;

    /**
     * @param id The id of this component
     */
    public RegistrationForm(String id) {
        super(id);

        createFeedbackPanel();
        createRegistrationForm();
        createSuccessMessage();
    }

    private void createFeedbackPanel() {
        add(new MyFencedFeedbackPanel("hiddenFeedback", this).setVisible(false));
    }

    private void createSuccessMessage() {
        successMessage = new Label("success", new StringResourceModel("success.message", this, null));
        successMessage.setVisible(false);
        add(successMessage);
    }

    private void createRegistrationForm() {
        createFields();

        form = new Form<User>("registrationForm") {
            @Override
            protected void onSubmit() {
                User user = getModelObject();
                String plainPassword = user.getPassword();
                String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
                user.setPassword(hashedPassword);
                user.setActivationKey(DigestUtils.md5Hex(BCrypt.gensalt()));
                user.getSystemroles().add(new Role(Roles.USER));

                try {
                    userManager.insertUser(user);
                    sendActivationMail(user);
                    successMessage.setVisible(true);
                    form.setVisible(false);
                } catch (UserAlreadyExistsException e) {
                    error(new StringResourceModel("error.usernameAlreadyTaken",
                            this, null));
                }
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

    private void sendActivationMail(User user) {
        String address = user.getEmail();
        String subject = "Successfully registered!";

        Map<String, String> variables = new HashMap<String, String>();
        variables.put("username", user.getUsername());
        variables.put("activationlink", createActivationLink(user));

        RegistrationMailTemplate template = new RegistrationMailTemplate();
        String message = template.asString(variables);

        mailService.sendMail(address, subject, message);
    }

    private String createActivationLink(User user) {
        PageParameters pp = new PageParameters();
        pp.set(0, user.getActivationKey());

        return RequestCycle.get().getUrlRenderer().renderFullUrl(Url.parse(urlFor(ActivateUserPage.class, pp)));
    }

    private ControlGroup getUsernameControlGroup() {
        String id = "usernameControlGroup";
        RequiredTextField<String> field = getUsernameField();
        return createControlGroup(id, field);
    }

    private void setUsernameField() {
        usernameField = new RequiredTextField<String>("username");
        usernameField.setLabel(new StringResourceModel("username.label", this, null));
        usernameField.add(new UniqueContentValidator(userManager.getAllUsernames()));
        usernameField.add(new Placeholder("username.placeholder", this));
    }

    private RequiredTextField<String> getUsernameField() {
        return usernameField;
    }

    private ControlGroup getEmailControlGroup() {
        String id = "emailControlGroup";
        EmailTextField field = getEmailField();
        return createControlGroup(id, field);
    }

    private void setEmailField() {
        email = new EmailTextField("email");
        email.setRequired(true);
        email.setLabel(new StringResourceModel("email.label", this, null));
        email.add(new UniqueContentValidator(userManager.getAllEmailAddresses()));
        email.add(new Placeholder("email.placeholder", this));
    }

    private EmailTextField getEmailField() {
        return email;
    }

    private ControlGroup getPasswordControlGroup() {
        String id = "passwordControlGroup";
        PasswordTextField field = getPasswordField();
        return createControlGroup(id, field);
    }

    private PasswordTextField getPasswordField() {
        return password;
    }

    private void setPasswordField() {
        password = new PasswordTextField("password");
        password.setLabel(new StringResourceModel("password.label", this, null));
    }

    private ControlGroup getPasswordConfirmControlGroup() {
        String id = "passwordConfirmControlGroup";
        PasswordTextField field = getPasswordConfirmField();
        return createControlGroup(id, field);
    }

    private PasswordTextField getPasswordConfirmField() {
        return passwordConfirm;
    }

    private void setPasswordConfirmField() {
        passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
        passwordConfirm.setLabel(new StringResourceModel("passwordConfirm.label", this, null));
    }

    private ControlGroup getButtonControlGroup() {
        String id = "buttonControlGroup";
        Button submitButton = getSubmitButton();
        return createControlGroup(id, submitButton);
    }

    private Button getSubmitButton() {
        return submitButton;
    }

    private void setSubmitButton() {
        submitButton = new Button("submit");
        submitButton.setModel(new StringResourceModel("submit.label", this, null));
    }

    @SuppressWarnings("rawtypes")
    private ControlGroup createControlGroup(String id, FormComponent... field) {
        ControlGroup cg = new ControlGroup(id);
        cg.add(field);
        return cg;
    }

    private EqualPasswordInputValidator getPasswordsEqualBehavior() {
        return new EqualPasswordInputValidator(getPasswordField(),
                getPasswordConfirmField());
    }
}