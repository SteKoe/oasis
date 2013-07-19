package de.stekoe.idss.component.form.registration;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.behavior.Placeholder;
import de.stekoe.idss.component.feedbackpanel.MyFencedFeedbackPanel;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.security.bcrypt.BCrypt;
import de.stekoe.idss.service.UserManager;

@SuppressWarnings("serial")
public class RegistrationForm extends Panel {

	@SpringBean
	private UserManager userManager;

	private User user = new User();

	private Form<User> form;

	private RequiredTextField<String> usernameField;
	private EmailTextField email;
	private PasswordTextField password;
	private PasswordTextField passwordConfirm;
	private Button submitButton;

	public RegistrationForm(String id) {
		super(id, null);

		createHiddenFeedbackPanel();
		createFields();
		createRegistrationForm();
	}

	/**
	 * This method adds a hidden {@code FencedFeedbackPanel} to the Panel.
	 * 
	 * <p>
	 * The purpose of {@link MyFencedFeedbackPanel} is to force the framework to
	 * not to print all the errors of the fields to the page.
	 */
	private void createHiddenFeedbackPanel() {
		// TODO: Is there a better way???
		add(new MyFencedFeedbackPanel("hiddenFeedback", this).setVisible(false));
	}

	private void createFields() {
		setUsernameField();
		setEmailField();
		setPasswordField();
		setPasswordConfirmField();
		setSubmitButton();
	}

	private void createRegistrationForm() {
		form = new Form<User>("registrationForm") {
			@Override
			protected void onSubmit() {
				User user = getModelObject();
				String plainPassword = user.getPassword();
				String hashedPassword = BCrypt.hashpw(plainPassword,
						BCrypt.gensalt());
				user.setPassword(hashedPassword);
				user.setActivationKey(DigestUtils.md5Hex(BCrypt.gensalt()));
				try {
					userManager.insertUser(user);
				} catch (UserAlreadyExistsException e) {
					error(new StringResourceModel("error.usernameAlreadyTaken",
							this, null));
				}
			}
		};
		form.setModel(new CompoundPropertyModel<User>(user));

		form.add(getUsernameControlGroup());
		form.add(getEmailControlGroup());
		form.add(getPasswordControlGroup());
		form.add(getPasswordConfirmControlGroup());
		form.add(getButtonControlGroup());

		form.add(getPasswordsEqualBehavior());

		add(form);
	}

	private ControlGroup getUsernameControlGroup() {
		String id = "usernameControlGroup";
		RequiredTextField<String> field = getUsernameField();
		return createControlGroup(id, field);
	}

	private void setUsernameField() {
		usernameField = new RequiredTextField<String>("username");
		usernameField.setLabel(new StringResourceModel("username.label", this,
				null));
		usernameField.add(new UniqueContentValidator(userManager
				.getAllUsernames()));
		usernameField.add(new Placeholder("username.placeholder", this));
	}

	private RequiredTextField<String> getUsernameField() {
		return usernameField;
	}

	private ControlGroup getEmailControlGroup() {
		String id = "emailControlGroup";
		EmailTextField field = getEmailField();
		return createControlGroup(id, field);
	}

	private void setEmailField() {
		email = new EmailTextField("email");
		email.setRequired(true);
		email.setLabel(new StringResourceModel("email.label", this, null));
		email.add(new UniqueContentValidator(userManager.getAllEmailAddresses()));
		email.add(new Placeholder("email.placeholder", this));
	}

	private EmailTextField getEmailField() {
		return email;
	}

	private ControlGroup getPasswordControlGroup() {
		String id = "passwordControlGroup";
		PasswordTextField field = getPasswordField();
		return createControlGroup(id, field);
	}

	private PasswordTextField getPasswordField() {
		return password;
	}

	private void setPasswordField() {
		password = new PasswordTextField("password");
		password.setLabel(new StringResourceModel("password.label", this, null));
	}

	private ControlGroup getPasswordConfirmControlGroup() {
		String id = "passwordConfirmControlGroup";
		PasswordTextField field = getPasswordConfirmField();
		return createControlGroup(id, field);
	}

	private PasswordTextField getPasswordConfirmField() {
		return passwordConfirm;
	}

	private void setPasswordConfirmField() {
		passwordConfirm = new PasswordTextField("passwordConfirm", Model.of(""));
		passwordConfirm.setLabel(new StringResourceModel(
				"passwordConfirm.label", this, null));
	}

	private ControlGroup getButtonControlGroup() {
		String id = "buttonControlGroup";
		Button submitButton = getSubmitButton();
		return createControlGroup(id, submitButton);
	}

	private Button getSubmitButton() {
		return submitButton;
	}

	private void setSubmitButton() {
		submitButton = new Button("submit");
		submitButton.setModel(new StringResourceModel("submit.label", this,
				null));
	}

	@SuppressWarnings("rawtypes")
	private ControlGroup createControlGroup(String id, FormComponent... field) {
		ControlGroup cg = new ControlGroup(id);
		cg.add(field);
		return cg;
	}

	private EqualPasswordInputValidator getPasswordsEqualBehavior() {
		return new EqualPasswordInputValidator(getPasswordField(),
				getPasswordConfirmField());
	}
}