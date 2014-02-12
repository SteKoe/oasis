package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class SystemRoleDAO extends GenericDAO<SystemRole> implements ISystemRoleDAO {

    @Override
    public SystemRole getRoleByName(String rolename) {
        return (SystemRole) getCurrentSession().createCriteria(SystemRole.class).add(Restrictions.eq("name", rolename)).uniqueResult();
    }

    @Override
    protected Class getPersistedClass() {
        return SystemRole.class;
    }
}
