package de.stekoe.idss.service.impl;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.dao.BaseTest;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.Systemrole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.security.bcrypt.BCrypt;
import de.stekoe.idss.service.UserManager;

public class UserManagerImplTest extends BaseTest {

	private static final String[] USERNAMES = {"Stephan","Benedikt","Robert","Jonas"};
	private static final String PASSWORT = "geheim";
	
	@Autowired
	private UserManager userManager;
	
	@Before
	public void setUp() throws Exception {
		for(int i = 0; i < USERNAMES.length; i++) {
			User user = new User();
			user.setUsername(USERNAMES[i]);
			user.setEmail(USERNAMES[i]+"@example.com");
			user.setPassword(BCrypt.hashpw(PASSWORT, BCrypt.gensalt()));
			userManager.insertUser(user);
		}
	}

	@Test
	public void getAllUsers() throws Exception {
		userManager.getAllUsers();
	}
	
	@Test(expected=UserAlreadyExistsException.class)
	public void duplicatedUsername() throws Exception {
		User duplicatedUser = new User();
		duplicatedUser.setUsername(USERNAMES[0]);
		
		userManager.insertUser(duplicatedUser);
	}
	
	@Test
	public void loginWorks() throws Exception {
		assertTrue(userManager.login(USERNAMES[0], PASSWORT));
	}
	
	@Test
	public void testForRoles() throws Exception {
		User user = userManager.findByUsername(USERNAMES[0]);
		user.getSystemroles().add(new Systemrole("ADMINISTRATOR"));
		userManager.update(user);
	}
}
