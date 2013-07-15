package de.stekoe.idss.service;

import java.util.List;

import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.User;

public interface UserManager {
	boolean insertUser(User user) throws UserAlreadyExistsException;
	boolean login(String username, String password);
	User findByUsername(String username);
	void update(User user);
	List<User> getAllUsers();
	User findByActivationCode(String code);
}
