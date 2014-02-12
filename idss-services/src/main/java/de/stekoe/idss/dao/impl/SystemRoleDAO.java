package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class SystemRoleDAO extends GenericDAO implements ISystemRoleDAO {

    @Override
    public void save(SystemRole entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Serializable id) {
        final SystemRole entity = findById(id);
        delete(entity);
    }

    @Override
    public void delete(SystemRole entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public SystemRole findById(Serializable id) {
        return (SystemRole) getCurrentSession().load(SystemRole.class, id);
    }

    @Override
    public List<SystemRole> findAll() {
        return getCurrentSession().createCriteria(SystemRole.class).list();
    }

    @Override
    public SystemRole getRoleByName(String rolename) {
        return (SystemRole) getCurrentSession().createCriteria(SystemRole.class).add(Restrictions.eq("name", rolename)).uniqueResult();
    }

}
