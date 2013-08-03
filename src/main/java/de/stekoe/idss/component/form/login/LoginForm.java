package de.stekoe.idss.component.form.login;


import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.service.UserManager;
import de.stekoe.idss.service.UserManager.LoginStatus;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LoginForm extends Panel {
    private static final Logger LOG = Logger.getLogger(LoginForm.class);

    @SpringBean
    public static UserManager userManager;

    private Label successMessage;

    /**
     * @param id The id of this component
     */
    public LoginForm(String id) {
        super(id);
        add(new MyFencedFeedbackPanel("hiddenFeedback", this));
        add(new SignInForm("loginForm"));
        createSuccessMessage();
    }

    private void createSuccessMessage() {
        successMessage = new Label("success", new StringResourceModel("success.message", this, null));
        successMessage.setVisible(false);
        add(successMessage);
    }

    /**
     * Private inner class which creates the SignInForm based on a StatelessForm.
     *
     * StatelessForm is used since the user should be able to insert credentials,
     * leave the pc and sign in at any later point.
     */
    @SuppressWarnings("rawtypes")
    private static class SignInForm extends StatelessForm {
        private String username;
        private String password;

        @SuppressWarnings("unchecked")
        public SignInForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(newUsernameTextField());
            add(newPasswordTextField());
            add(newButton());
        }


        private ControlGroup newPasswordTextField() {
            ControlGroup cg = new ControlGroup("passwordControlGroup");
            PasswordTextField passwordTextField = new PasswordTextField("password");
            passwordTextField.setLabel(new StringResourceModel("password.label", this, null));
            cg.add(passwordTextField);
            return cg;
        }

        private ControlGroup newUsernameTextField() {
            ControlGroup cg = new ControlGroup("usernameControlGroup");
            TextField usernameTextField = new TextField("username");
            usernameTextField.setLabel(new StringResourceModel("username.label", this, null));
            cg.add(usernameTextField);
            return cg;
        }

        private ControlGroup newButton() {
            ControlGroup cg = new ControlGroup("buttonControlGroup");
            Button button = new Button("submit");
            button.setModel(new StringResourceModel("submit.label", this, null));
            cg.add(button);
            return cg;
        }

        @Override
        protected void onSubmit() {
            if(username != null && password != null) {
                LoginStatus loginStatus = userManager.login(username, password);
                if(LoginStatus.SUCCESS.equals(loginStatus)) {
                    LOG.info(String.format("User %s has logged in!", username));
                    setResponsePage(getApplication().getHomePage());
                } else {
                    error("asd");
                }
            }
        }

    }
}