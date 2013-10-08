package de.stekoe.idss.page;

import de.stekoe.idss.component.form.auth.login.LoginForm;


/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class LoginPage extends LayoutPage {

    /**
     * Construct.
     */
    public LoginPage() {
        add(new LoginForm("loginForm"));
    }
}
