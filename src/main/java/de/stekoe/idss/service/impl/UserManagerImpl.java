package de.stekoe.idss.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.dao.UserDAO;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.security.bcrypt.BCrypt;
import de.stekoe.idss.service.UserManager;

@Service
public class UserManagerImpl implements UserManager {

	private Logger LOG = Logger.getLogger(UserManagerImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public boolean insertUser(User user) throws UserAlreadyExistsException {
		User existentUser = userDAO.findByUsername(user.getUsername());
		if(existentUser == null) {
			userDAO.insert(user);
			return true;
		}
		else {
			LOG.warn("Tried to insert new user with existing username!");
			throw new UserAlreadyExistsException();
		}
	}

	@Transactional
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Transactional
	public void update(User entity) {
		userDAO.update(entity);
	}

	@Override
	@Transactional
	public boolean login(String username, String password) {
		User user = userDAO.findByUsername(username);
		return BCrypt.checkpw(password, user.getPassword());
	}

	@Override
	@Transactional
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}

	@Override
	@Transactional
	public User findByActivationCode(String code) {
		return userDAO.findByActivationCode(code);
	}
}
