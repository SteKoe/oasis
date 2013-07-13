package de.stekoe.idss.dao;

import de.stekoe.idss.model.User;

public interface UserDAO {
	User findByUsername(String username);
	void saveOrUpdate(User entity);
}
