package de.stekoe.idss.page;

import de.stekoe.idss.page.component.form.auth.register.RegistrationForm;

@SuppressWarnings("serial")
public class RegistrationPage extends ContainerLayoutPage {
    public RegistrationPage() {
        setTitle(getString("form.register.title"));
        add(new RegistrationForm("form"));
    }
}
