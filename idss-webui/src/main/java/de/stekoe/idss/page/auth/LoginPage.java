package de.stekoe.idss.page.auth;

import de.stekoe.idss.page.component.form.auth.login.LoginForm;
import de.stekoe.idss.page.LayoutPage;


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
