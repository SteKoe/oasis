package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Transactional
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

    @Override
    public void delete(User user) {
        getCurrentSession().delete(user);
    }

    @Override
    public User findByActivationCode(java.lang.String code) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("activationKey", code));
        return (User) criteria.uniqueResult();
    }

    @Override
    public User findByEmail(java.lang.String email) {
        Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        return (User) criteria.uniqueResult();
    }

    @Override
    public List<User> findAllByUsername(String username) {
        final Criteria criteria = getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.ilike("username", "%"+username+"%"));
        return criteria.list();
    }

    @Override
    public User findById(Serializable id) {
        return (User) getCurrentSession().get(User.class, id);
    }
}
