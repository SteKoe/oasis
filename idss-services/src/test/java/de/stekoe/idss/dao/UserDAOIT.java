package de.stekoe.idss.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;
import de.stekoe.idss.repository.UserRepository;
import de.stekoe.idss.service.SystemRoleService;

public class UserDAOIT extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SystemRoleService systemRoleService;

    @Before
    public void setUp() {
        SystemRole user = new SystemRole();
        user.setName(SystemRole.USER);
        systemRoleService.save(user);

        SystemRole admin = new SystemRole();
        admin.setName(SystemRole.ADMIN);
        systemRoleService.save(admin);

        List<SystemRole> systemRoles = systemRoleService.findAll();
        assertThat(systemRoles.size(), Is.is(2));
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
        userRepository.save(user);

        User retrievedUser = userRepository.findByUsername("miau");
        assertThat(retrievedUser.getUsername(), IsEqual.equalTo(user.getUsername()));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void needUsername() throws Exception {
        User user = new User();
        user.setUsername(null);
        user.setPassword("abc");
        user.setEmail("asd");
        userRepository.save(user);
    }

    @Test
    public void insertUserWithRoles() throws Exception {
        User userWithRoles = getUser();
        userWithRoles.getRoles().add(systemRoleService.getRoleByName(SystemRole.USER));
        userWithRoles.getRoles().add(systemRoleService.getRoleByName(SystemRole.ADMIN));
        userRepository.save(userWithRoles);

        User retrievedUser = userRepository.findByUsername("hans");
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

        userRepository.save(user);
    }

}
