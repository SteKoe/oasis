package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserProfileDAO;
import de.stekoe.idss.model.UserProfile;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class UserProfileDAO extends GenericDAO<UserProfile> implements IUserProfileDAO {
    @Override
    protected Class getPersistedClass() {
        return UserProfile.class;
    }
}
