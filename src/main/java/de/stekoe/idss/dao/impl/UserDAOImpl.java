package de.stekoe.idss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import de.stekoe.idss.dao.UserDAO;
import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public class UserDAOImpl extends GenericDAOImpl implements UserDAO {

    @Override
    public User findByUsername(String username) {
        Criteria criteria = getCurrentSession().createCriteria(User.class).add(Restrictions.eq("username", username));
        return (User) criteria.uniqueResult();
    }

    @Override
    public void update(User entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public List<User> getAllUsers() {
        return getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public boolean insert(User user) {
        try {
            getCurrentSession().save(user);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public User findByActivationCode(String code) {
        return (User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("activationKey", code)).uniqueResult();
    }

    @Override
    public User findByEmail(String email) {
        return (User) getCurrentSession().createCriteria(User.class).add(Restrictions.eq("email", email)).uniqueResult();
    }
}
