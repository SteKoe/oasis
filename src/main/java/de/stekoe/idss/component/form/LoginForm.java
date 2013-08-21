package de.stekoe.idss.component.form;


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
import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;
import de.stekoe.idss.service.IUserService.LoginStatus;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LoginForm extends Panel {
    private static final Logger LOG = Logger.getLogger(LoginForm.class);

    @SpringBean
    public static IUserService userManager;

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
                    User user = IDSSSession.get().getUser();
                    LOG.info(user.toString());
                    setResponsePage(getApplication().getHomePage());
                } else {
                    LOG.info(String.format("Login for User %s returned status %s.", username, loginStatus.toString()));

                    boolean error = true;
                    String message;
                    switch (loginStatus) {
                        case USER_NOT_ACTIVATED:
                            message = getString("login.error.not_activated");
                            error = false;
                            break;
                        case USER_NOT_FOUND:
                            message = getString("login.error.user_not_found");
                            break;
                        case WRONG_PASSWORD:
                            message = getString("login.error.wrong_password");
                            break;
                        default:
                            message = getString("login.error");
                            break;
                    }

                    if(error) {
                        error(message);
                    } else {
                        info(message);
                    }
                }
            }
        }

    }
}