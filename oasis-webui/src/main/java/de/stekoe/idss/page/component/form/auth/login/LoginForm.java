package de.stekoe.idss.page.component.form.auth.login;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.company.CompanyListPage;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

@SuppressWarnings("serial")
public class LoginForm extends Panel {
    private static final Logger LOG = Logger.getLogger(LoginForm.class);

    @SpringBean
    private AuthService authService;

    /**
     * @param id The id of this component
     */
    public LoginForm(String id) {
        super(id);
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
            add(new FormGroup("group.username").add(usernameTextField));
            usernameTextField.setLabel(new ResourceModel("label.username"));
            usernameTextField.setRequired(true);

            PasswordTextField passwordTextField = new PasswordTextField("password");
            add(new FormGroup("group.password").add(passwordTextField));
            passwordTextField.setLabel(new ResourceModel("label.password"));
            passwordTextField.setRequired(true);

            BookmarkablePageLink<RequestNewPasswordPage> passwordLostLink = new BookmarkablePageLink<RequestNewPasswordPage>("link.password.lost", RequestNewPasswordPage.class);
            add(passwordLostLink);

            BookmarkablePageLink<Object> linkRegister = new BookmarkablePageLink<>("link.register", RegistrationPage.class);
            add(linkRegister);

            Button submitButton = new Button("submit");
            submitButton.setModel(new ResourceModel("label.submit"));
            add(submitButton);

            add(new MarkRequiredFieldsBehavior());
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

                    if(!WebSession.get().getUser().isAdmin()) {
                        setResponsePage(ProjectListPage.class);
                    } else {
                        setResponsePage(CompanyListPage.class);
                    }
                } else {
                    boolean error = true;
                    String message;
                    switch (authStatus) {
                        case USER_NOT_ACTIVATED:
                            message = getString("message.login.error.not_activated");
                            error = false;
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