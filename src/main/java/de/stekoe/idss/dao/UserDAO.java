package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.User;

public interface UserDAO {
	User findByUsername(String username);
	void update(User entity);
	List<User> getAllUsers();
	boolean insert(User user);
	User findByActivationCode(String code);
}
