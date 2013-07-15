package de.stekoe.idss.dao;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.core.IsEqual;
import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import de.stekoe.idss.model.Systemrole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserProfile;


public class UserDAOTest extends BaseTest {

	@Autowired
	private UserDAO userDAO;
	
	@Test
	@Rollback(true)
	public void insertNewUser() throws Exception {
		User user = new User();
		user.setUsername("hans");
		user.setPassword("geheim");
		userDAO.insert(user);
		flush();
		
		User retrievedUser = (User) getCurrentSession().get(User.class, user.getId());
		System.out.println(retrievedUser.toString());
		assertThat(retrievedUser.getUsername(), IsEqual.equalTo("hans"));
	}
	
	@Test(expected=PropertyValueException.class)
	@Rollback(true)
	public void needUsername() throws Exception {
		User user = new User();
		user.setPassword("geheim");
		userDAO.insert(user);
		flush();
	}
	
	@Test(expected=PropertyValueException.class)
	@Rollback(true)
	public void needPassword() throws Exception {
		User user = new User();
		user.setUsername("hans");
		userDAO.insert(user);
		flush();
	}
	
	@Test
	@Rollback(true)
	public void insertUserWithRoles() throws Exception {
		Systemrole admin = new Systemrole("ADMINISTRATOR");
		Systemrole user = new Systemrole("USER");
		
		User userWithRoles = new User();
		userWithRoles.setUsername("hans franz");
		userWithRoles.setPassword("geheim");
		userWithRoles.getSystemroles().add(admin);
		userWithRoles.getSystemroles().add(user);
		
		userDAO.insert(userWithRoles);
		flush();
		
		User retrievedUser = (User)getCurrentSession().get(User.class, userWithRoles.getId());
		System.out.println(retrievedUser.toString());
		assertTrue(retrievedUser.getSystemroles().size() == 2);
	}
	
	@Test
	@Rollback(true)
	public void insertUserWithProfile() throws Exception {
		Calendar bd = Calendar.getInstance();
		bd.set(1987, 3, 5);
		
		UserProfile profile = new UserProfile();
		profile.setBirthdate(new Date(bd.getTimeInMillis()));
		profile.setFirstname("Stephan");
		profile.setSurename("Köninger");
		
		User user = new User();
		user.setUsername("stephan.koeninger");
		user.setPassword("geheim");
		user.setUserProfile(profile);
		
		userDAO.insert(user);
	}

}
