package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class SystemRoleDAO extends GenericDAO implements ISystemRoleDAO {

    @Override
    public void update(SystemRole role) {
        getCurrentSession().update(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SystemRole> getAllRoles() {
        return getCurrentSession().createCriteria(SystemRole.class).list();
    }

    @Override
    public boolean save(SystemRole role) {
        try {
            getCurrentSession().save(role);
        } catch (HibernateException he) {
            return false;
        }
        return true;
    }

    @Override
    public SystemRole getRoleByName(String rolename) {
        return (SystemRole) getCurrentSession().createCriteria(SystemRole.class).add(Restrictions.eq("name", rolename)).uniqueResult();
    }

}
