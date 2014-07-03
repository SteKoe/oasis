package de.stekoe.idss.page.user;

import de.stekoe.idss.page.AuthAdminPage;
import de.stekoe.idss.page.component.form.user.CreateUserForm;

/**
 * @author Stephan Koeninger 
 */
public class CreateUserPage extends AuthAdminPage {

    public CreateUserPage() {
        add(new CreateUserForm("createUserForm"));
    }

}
