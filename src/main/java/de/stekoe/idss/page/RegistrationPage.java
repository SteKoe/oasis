package de.stekoe.idss.page;

import de.stekoe.idss.component.form.registration.RegistrationForm;

public class RegistrationPage extends LayoutPage {
	
	public RegistrationPage() {
		add(new RegistrationForm("form"));
	}
}
