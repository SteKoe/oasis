package de.stekoe.idss.service.impl;

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
			userDAO.saveOrUpdate(user);
			return true;
		}
		else {
			throw new UserAlreadyExistsException();
		}
	}

	@Transactional
	public User findByUsername(String username) {
		return userDAO.findByUsername(username);
	}

	@Transactional
	public void saveOrUpdate(User entity) {
		userDAO.saveOrUpdate(entity);
	}

	@Override
	@Transactional
	public boolean login(String username, String password) {
		User user = userDAO.findByUsername(username);
		return BCrypt.checkpw(password, user.getPassword());
	}
}
