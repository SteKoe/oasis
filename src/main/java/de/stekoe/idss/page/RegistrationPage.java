package de.stekoe.idss.page;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;

public class RegistrationPage extends LayoutPage {
	
	private String message = "I am empty... Fill me with your content!";
	
	public RegistrationPage() {
		
		PropertyModel<String> messageModel = new PropertyModel<String>(this, "message");
		
		add(new Label("msg", messageModel));
		
		Form<?> form = new Form("form");
		form.add(new TextField<String>("msgInput", messageModel));
		add(form);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
