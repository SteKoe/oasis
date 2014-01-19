package de.stekoe.idss.page.auth;

import de.stekoe.idss.component.form.auth.register.RegistrationForm;
import de.stekoe.idss.page.LayoutPage;


/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class RegistrationPage extends LayoutPage {
    public RegistrationPage() {
        add(new RegistrationForm("form"));
    }
}
