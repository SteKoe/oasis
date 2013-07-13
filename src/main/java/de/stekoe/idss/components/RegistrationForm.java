package de.stekoe.idss.components;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;

import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class RegistrationForm extends Panel {

	public RegistrationForm(String id) {
		super(id, null);
		
		Form<User> form = new Form<User>("registrationForm"){
			@Override
			protected void onSubmit() {
				User user = (User) this.getModelObject();
				System.out.println(user);
			}
		};
		form.setModel(new CompoundPropertyModel<User>(new LoadableDetachableUserModel()));
		
		form.add(new TextField<String>("username"));
		form.add(new PasswordTextField("password"));
		
		add(form);
	}

	private class LoadableDetachableUserModel extends LoadableDetachableModel<User> {
		@Override
		protected User load() {
			return new User();
		}
		
	}
}