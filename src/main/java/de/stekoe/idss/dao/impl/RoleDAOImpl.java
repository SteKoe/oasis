package de.stekoe.idss.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import de.stekoe.idss.dao.RoleDAO;
import de.stekoe.idss.model.Role;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public class RoleDAOImpl extends GenericDAOImpl implements RoleDAO {

    @Override
    public void update(Role role) {
        getCurrentSession().update(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getAllRoles() {
        return getCurrentSession().createCriteria(Role.class).list();
    }

    @Override
    public boolean insert(Role role) {
        try {
            getCurrentSession().save(role);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public Role getRoleByName(String rolename) {
        return (Role) getCurrentSession().createCriteria(Role.class).add(Restrictions.eq("roleName", rolename)).uniqueResult();
    }

}
