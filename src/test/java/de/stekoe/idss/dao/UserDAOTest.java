package de.stekoe.idss.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;

public class UserDAOTest extends BaseTest {

    @Autowired
    private UserDAO userDAO;

    private User getUser() {
        User user = new User();
        user.setUsername("hans");
        user.setEmail("hans@example.com");
        user.setPassword("geheim");
        return user;
    }

    @Test
    public void insertNewUser() throws Exception {
        User user = getUser();
        userDAO.save(user);

        User retrievedUser = userDAO.findByUsername("hans");
        assertThat(retrievedUser.getUsername(), IsEqual.equalTo(user.getUsername()));
    }

    @Test
    public void needUsername() throws Exception {
        User user = new User();
        user.setUsername("miau");
        assertFalse(userDAO.save(user));
    }

    @Test
    public void insertUserWithRoles() throws Exception {
        User userWithRoles = getUser();
        Role userRole = new Role();
        userRole.setRoleName(Roles.USER);
        userWithRoles.getRoles().add(userRole);
        Role adminRole = new Role();
        adminRole.setRoleName(Roles.ADMIN);
        userWithRoles.getRoles().add(adminRole);
        userDAO.save(userWithRoles);

        User retrievedUser = userDAO.findByUsername("hans");
        assertTrue(retrievedUser.getRoles().size() == 2);
    }

    @Test
    public void insertUserWithProfile() throws Exception {
        Calendar bd = Calendar.getInstance();
        bd.set(1987, 3, 5);

        UserProfile profile = new UserProfile();
        profile.setBirthdate(new Date(bd.getTimeInMillis()));
        profile.setFirstname("Stephan");
        profile.setSurename("Köninger");

        User user = getUser();
        user.setUserProfile(profile);

        userDAO.save(user);
    }

}
