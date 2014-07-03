package de.stekoe.idss.page;

import de.stekoe.idss.page.component.form.auth.login.LoginForm;

@SuppressWarnings("serial")
public class LoginPage extends ContainerLayoutPage {
    public LoginPage() {
        setTitle(getString("form.login.title"));
        add(new LoginForm("loginForm"));
    }
}
