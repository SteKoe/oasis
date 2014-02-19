package de.stekoe.idss.component.form.auth.login;

import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LoginForm extends Panel {
    private static final Logger LOG = Logger.getLogger(LoginForm.class);

    @SpringBean
    private AuthService authService;

    private Label successMessage;

    /**
     * @param id The id of this component
     */
    public LoginForm(String id) {
        super(id);
        add(new MyFencedFeedbackPanel("hiddenFeedback", this));
        add(new SignInForm("loginForm"));
    }

    /**
     * Private inner class which creates the SignInForm based on a StatelessForm.
     *
     * StatelessForm is used since the user should be able to insert credentials,
     * leave the pc and sign in at any later point.
     */
    @SuppressWarnings("rawtypes")
    private class SignInForm extends StatelessForm {
        private String username;
        private String password;

        @SuppressWarnings("unchecked")
        public SignInForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));

            TextField usernameTextField = new TextField("username");
            add(usernameTextField);

            PasswordTextField passwordTextField = new PasswordTextField("password");
            add(passwordTextField);

            Button submitButton = new Button("submit");
            submitButton.setModel(Model.of(getString("label.submit")));
            add(submitButton);
        }

        @Override
        protected void onSubmit() {

            if (username != null && password != null) {

                final AuthStatus authStatus = authService.authenticate(username, password);
                LOG.info(String.format("Login for User %s returned status %s.", username, authStatus.toString()));

                if (authStatus.equals(AuthStatus.SUCCESS)) {
                    WebSession.get().success(getString("message.login.success"));
                    WebSession.get().signIn(username, password);

                    // Try to redirect to page which required authentication
                    continueToOriginalDestination();
                    // If that did not happen, redirect to standard landing page
                    setResponsePage(ProjectListPage.class);
                } else {
                    boolean error = true;
                    String message;
                    switch (authStatus) {
                        case USER_NOT_ACTIVATED:
                            message = getString("message.login.error.not_activated");
                            error = false;
                            break;
                        case USER_NOT_FOUND:
                            message = getString("message.login.error.user_not_found");
                            break;
                        case WRONG_PASSWORD:
                            message = getString("message.login.error.wrong_password");
                            break;
                        default:
                            message = getString("message.login.error");
                            break;
                    }

                    if (error) {
                        WebSession.get().error(message);
                    } else {
                        WebSession.get().info(message);
                    }
                }
            }
        }

    }
}