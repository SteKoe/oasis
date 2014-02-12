package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IPermissionDAO;
import de.stekoe.idss.model.Permission;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class PermissionDAO extends GenericDAO implements IPermissionDAO {
    @Override
    public void save(Permission entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Serializable id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Permission entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public Permission findById(Serializable id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Permission> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
