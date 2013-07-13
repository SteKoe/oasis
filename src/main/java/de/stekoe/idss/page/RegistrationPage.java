package de.stekoe.idss.page;

import de.stekoe.idss.components.RegistrationForm;

public class RegistrationPage extends LayoutPage {
	
	private String message = "I am empty... Fill me with your content!";
	
	public RegistrationPage() {
		add(new RegistrationForm("form"));
	}
}
