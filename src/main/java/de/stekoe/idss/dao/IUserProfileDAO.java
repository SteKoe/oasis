package de.stekoe.idss.dao;

import de.stekoe.idss.model.UserProfile;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IUserProfileDAO {
    /**
     * @param entity UserProfile which should be saved or updated in database.
     */
    void saveOrUpdate(UserProfile entity);
}
