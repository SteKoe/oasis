package de.stekoe.idss.page;

import java.util.HashSet;
import java.util.Set;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.component.form.registration.RegistrationForm;
import de.stekoe.idss.model.Systemrole;
import de.stekoe.idss.model.User;

@SuppressWarnings("serial")
public class RegistrationPage extends LayoutPage {

    public RegistrationPage() {
        add(new RegistrationForm("form"));

        Set<Systemrole> systemroles = new HashSet<Systemrole>();
        systemroles.add(new Systemrole(Systemrole.USER));

        User user = new User();
        user.setUsername("username");
        user.setEmail("username@example.com");
        user.setPassword("geheim");
        user.setSystemroles(systemroles);

        IDSSSession session = getSession();
        session.setUser(user);
    }
}
