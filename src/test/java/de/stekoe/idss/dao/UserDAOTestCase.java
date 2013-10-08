package de.stekoe.idss.dao;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class UserDAOTestCase extends BaseTest {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private SystemRoleDAO systemRoleDAO;

    @Before
    public void setUp() {
        SystemRole user = new SystemRole();
        user.setName(SystemRole.USER);
        systemRoleDAO.save(user);

        SystemRole admin = new SystemRole();
        admin.setName(SystemRole.ADMIN);
        systemRoleDAO.save(admin);

        assertThat(systemRoleDAO.getAllRoles().size(), Is.is(2));
    }

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
        user.setUsername("miau");
        userDAO.save(user);

        User retrievedUser = userDAO.findByUsername("miau");
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
        userWithRoles.getRoles().add(systemRoleDAO.getRoleByName(SystemRole.USER));
        userWithRoles.getRoles().add(systemRoleDAO.getRoleByName(SystemRole.ADMIN));
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
        profile.setSurname("Köninger");

        User user = getUser();
        user.setProfile(profile);

        userDAO.save(user);
    }

}
