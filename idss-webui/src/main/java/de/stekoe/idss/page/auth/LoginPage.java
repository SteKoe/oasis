package de.stekoe.idss.page.auth;

import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.component.form.auth.login.LoginForm;


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
