package de.stekoe.idss.component.form.login;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.service.UserManager;
import de.stekoe.idss.service.UserManager.LoginStatus;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LoginForm extends Panel {

    @SpringBean
    private UserManager userManager;

    private final User user = new User();

    private Form<User> form;

    private RequiredTextField<String> usernameField;
    private PasswordTextField password;
    private Button submitButton;

    private Label successMessage;

    /**
     * @param id The id of this component
     */
    public LoginForm(String id) {
        super(id);

        createFeedbackPanel();
        createRegistrationForm();
        createSuccessMessage();
    }

    private void createFeedbackPanel() {
        add(new MyFencedFeedbackPanel("hiddenFeedback", this));
    }

    private void createSuccessMessage() {
        successMessage = new Label("success", new StringResourceModel("success.message", this, null));
        successMessage.setVisible(false);
        add(successMessage);
    }

    private void createRegistrationForm() {
        createFields();

        form = new Form<User>("loginForm") {
            @Override
            protected void onSubmit() {
                User user = getModelObject();
                LoginStatus loginStatus = userManager.login(user.getUsername(), user.getPassword());
                if(UserManager.LoginStatus.SUCCESS.equals(loginStatus)) {
                    IDSSSession.get().setUser(user);
                    setResponsePage(HomePage.class);
                } else {
                    if(loginStatus.equals(UserManager.LoginStatus.WRONG_PASSWORD)) {
                        warn("Falsches password!");
                    }
                    if(loginStatus.equals(UserManager.LoginStatus.USER_NOT_ACTIVATED)) {
                        info("Benutzerkonto nicht aktiviert!");
                    }
                    if(loginStatus.equals(UserManager.LoginStatus.USER_NOT_FOUND)) {
                        warn("Kein Konto mit den angegeben Daten gefunden!");
                    }
                }
            }
        };
        form.setModel(new CompoundPropertyModel<User>(user));

        form.add(getUsernameControlGroup());
        form.add(getPasswordControlGroup());
        form.add(getButtonControlGroup());

        add(form);
    }

    private void createFields() {
        setUsernameField();
        setPasswordField();
        setSubmitButton();
    }

    private ControlGroup getUsernameControlGroup() {
        String id = "usernameControlGroup";
        RequiredTextField<String> field = getUsernameField();
        return createControlGroup(id, field);
    }

    private void setUsernameField() {
        usernameField = new RequiredTextField<String>("username");
        usernameField.setLabel(new StringResourceModel("username.label", this, null));
    }

    private RequiredTextField<String> getUsernameField() {
        return usernameField;
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
}