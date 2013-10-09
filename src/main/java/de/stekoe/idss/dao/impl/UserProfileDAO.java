package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserProfileDAO;
import org.springframework.stereotype.Service;

import de.stekoe.idss.model.UserProfile;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class UserProfileDAO extends GenericDAO implements
        IUserProfileDAO {

    @Override
    public void saveOrUpdate(UserProfile entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

}
