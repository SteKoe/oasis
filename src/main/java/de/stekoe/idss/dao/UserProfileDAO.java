package de.stekoe.idss.dao;

import de.stekoe.idss.model.UserProfile;

public interface UserProfileDAO {
	void saveOrUpdate(UserProfile entity);
}
