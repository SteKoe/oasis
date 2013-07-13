package de.stekoe.idss.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import de.stekoe.idss.dao.UserDAO;
import de.stekoe.idss.model.User;

@Service
public class UserDAOImpl extends GenericDAOImpl implements UserDAO {

	@Override
	public User findByUsername(String username) {
		Criteria criteria = getCurrentSession().createCriteria(User.class).add(Restrictions.eq("username", username));
		return (User) criteria.uniqueResult();
	}

	@Override
	public void saveOrUpdate(User entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

}
