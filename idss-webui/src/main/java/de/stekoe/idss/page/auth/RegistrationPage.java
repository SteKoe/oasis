package de.stekoe.idss.page.auth;

import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.component.form.auth.register.RegistrationForm;


/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class RegistrationPage extends LayoutPage {
    public RegistrationPage() {
        add(new RegistrationForm("form"));
    }
}
