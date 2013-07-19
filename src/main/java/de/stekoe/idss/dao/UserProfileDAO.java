package de.stekoe.idss.dao;

import de.stekoe.idss.model.UserProfile;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface UserProfileDAO {
    void saveOrUpdate(UserProfile entity);
}
