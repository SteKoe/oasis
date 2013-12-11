package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
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
    public List<User> findAll() {
        return getCurrentSession().createCriteria(User.class).list();
    }

    @Override
    public void save(User user) {
        getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void delete(Serializable id) {
        final User user = findById(id);
        delete(user);
    }

    /**
     * Soft deletes a user by resetting personal data without removing the entity itself
     * in order to ensure integrity of the whole system.
     * @param entity
     */
    @Override
    public void delete(User entity) {
        // Reset username with "user" and "id"
        final String[] split = entity.getId().split("-");
        entity.setUsername("User_" + split[0]);
        entity.setUserStatus(UserStatus.DELETED);
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
    public User findById(Serializable id) {
        return (User) getCurrentSession().get(User.class, id);
    }
}
