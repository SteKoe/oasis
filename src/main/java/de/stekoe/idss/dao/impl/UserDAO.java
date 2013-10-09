package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class UserDAO extends GenericDAO implements IUserDAO {
    private static final Logger LOG = Logger.getLogger(UserDAO.class);

    @Override
    public User findByUsername(String username) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }


    @Override
    public List<User> getAllUsers() {
        return getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public boolean save(User user) {
        try {
            getCurrentSession().saveOrUpdate(user);
        } catch (Exception e) {
            LOG.warn("Error while saving/updating user!", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        getCurrentSession().update(user);
        return false;
    }

    @Override
    public User findByActivationCode(String code) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("activationKey", code));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findByEmail(String email) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findById(String id) {
        return (User) getCurrentSession().get(User.class, id);
    }
}
