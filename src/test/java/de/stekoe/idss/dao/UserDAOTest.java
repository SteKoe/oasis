package de.stekoe.idss.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.core.IsEqual;
import org.hibernate.AssertionFailure;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import de.stekoe.idss.model.Systemrole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;

public class UserDAOTest extends BaseTest {

	@Autowired
	private UserDAO userDAO;
	private User user;

	@Before
	public void setUp() {
		user = new User();
		user.setUsername("hans");
		user.setEmail("hans@example.com");
		user.setPassword("geheim");
	}

	@Test
	@Rollback(true)
	public void insertNewUser() throws Exception {
		userDAO.insert(user);
		User retrievedUser = (User) getCurrentSession().get(User.class,
				user.getId());
		assertThat(retrievedUser.getUsername(),
				IsEqual.equalTo(user.getUsername()));
	}

	@Test(expected = AssertionFailure.class)
	public void needUsername() throws Exception {
		User user = new User();
		user.setUsername("asd");
		userDAO.insert(user);
		flush();
	}

	@Test
	public void insertUserWithRoles() throws Exception {
		Systemrole admin = new Systemrole("ADMINISTRATOR");
		Systemrole user = new Systemrole("USER");

		User userWithRoles = this.user;
		userWithRoles.getSystemroles().add(admin);
		userWithRoles.getSystemroles().add(user);

		userDAO.insert(userWithRoles);

		User retrievedUser = (User) getCurrentSession().get(User.class,
				userWithRoles.getId());
		assertTrue(retrievedUser.getSystemroles().size() == 2);
	}

	@Test
	public void insertUserWithProfile() throws Exception {
		Calendar bd = Calendar.getInstance();
		bd.set(1987, 3, 5);

		UserProfile profile = new UserProfile();
		profile.setBirthdate(new Date(bd.getTimeInMillis()));
		profile.setFirstname("Stephan");
		profile.setSurename("Köninger");

		user.setUserProfile(profile);

		userDAO.insert(user);
	}

}
