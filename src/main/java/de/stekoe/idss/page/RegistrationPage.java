package de.stekoe.idss.page;

import de.stekoe.idss.component.form.registration.RegistrationForm;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class RegistrationPage extends LayoutPage {

    /**
     * Construct.
     */
    public RegistrationPage() {
        add(new RegistrationForm("form"));

    }
}
