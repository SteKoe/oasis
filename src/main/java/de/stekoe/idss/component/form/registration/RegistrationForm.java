package de.stekoe.idss.component.form.registration;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class RegistrationForm extends Panel {

	public RegistrationForm(String id) {
		super(id, null);
		
		User user = new User();
		
		Form<User> form = new Form<User>("registrationForm"){
			@Override
			protected void onSubmit() {
				User user = (User) this.getModelObject();
				System.out.println(user);
			}
		};
		form.setModel(new CompoundPropertyModel<User>(user));
		
		form.add(new TextField<String>("username"));
		form.add(new PasswordTextField("password"));
		
		add(form);
	}
}