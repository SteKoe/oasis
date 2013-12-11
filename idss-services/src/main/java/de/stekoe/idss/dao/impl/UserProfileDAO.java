package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserProfileDAO;
import de.stekoe.idss.model.UserProfile;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class UserProfileDAO extends GenericDAO implements IUserProfileDAO {

    @Override
    public void save(UserProfile entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void delete(Serializable id) {
        final UserProfile profile = findById(id);
        delete(profile);
    }

    @Override
    public void delete(UserProfile entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public UserProfile findById(Serializable id) {
        return (UserProfile) getCurrentSession().load(UserProfile.class, id);
    }

    @Override
    public List<UserProfile> findAll() {
        return getCurrentSession().createCriteria(UserProfile.class).list();
    }
}
