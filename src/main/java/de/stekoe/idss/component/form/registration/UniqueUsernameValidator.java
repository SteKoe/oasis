package de.stekoe.idss.component.form.registration;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserManager;

@SuppressWarnings("serial")
public class UniqueUsernameValidator implements IValidator<String> {

	@SpringBean
	private UserManager userDAO;
	
	private List<User> users;

	public UniqueUsernameValidator(List<User> allUsers) {
		this.users = allUsers;
	}

	@Override
	public void validate(IValidatable<String> validatable) {
		String username = validatable.getValue();

		for (User u : this.users) {
			if (u.getUsername().equals(username)) {
				error(validatable, "UsernameMustBeUnique");
				return;
			}
		}
	}

	private void error(IValidatable<String> validatable, String errorKey) {
		ValidationError error = new ValidationError();
		error.addKey(getClass().getSimpleName() + "." + errorKey);
		validatable.error(error);
	}
}
