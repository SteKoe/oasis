package de.stekoe.idss.dao.impl;

import org.springframework.stereotype.Service;

import de.stekoe.idss.dao.UserProfileDAO;
import de.stekoe.idss.model.UserProfile;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public class UserProfileDAOImpl extends GenericDAOImpl implements
        UserProfileDAO {

    @Override
    public void saveOrUpdate(UserProfile entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

}
