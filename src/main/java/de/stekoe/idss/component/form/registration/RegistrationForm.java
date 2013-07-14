package de.stekoe.idss.component.form.registration;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.stekoe.idss.component.behavior.Placeholder;
import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class RegistrationForm extends Panel {

	private User user = new User();
	
	public RegistrationForm(String id) {
		super(id, null);
		
		Form<User> form = new Form<User>("registrationForm"){
			@Override
			protected void onSubmit() {
				User user = (User) this.getModelObject();
				System.out.println(user);
			}
		};
		form.setModel(new CompoundPropertyModel<User>(user));
		
		TextField<String> username = new TextField<String>("username");
		username.add(new Placeholder("form.field.username.placeholder", this));
		form.add(username);
		
		form.add(new PasswordTextField("password"));
		
		add(form);
	}
}