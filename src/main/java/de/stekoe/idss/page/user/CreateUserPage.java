package de.stekoe.idss.page.user;

import de.stekoe.idss.component.form.user.CreateUserForm;
import de.stekoe.idss.page.AuthAdminPage;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateUserPage extends AuthAdminPage {

    public CreateUserPage() {
        add(new CreateUserForm("createUserForm"));
    }

}
