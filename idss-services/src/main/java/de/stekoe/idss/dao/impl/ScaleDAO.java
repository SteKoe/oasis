package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IScaleDAO;
import de.stekoe.idss.model.scale.Scale;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Transactional
public class ScaleDAO extends GenericDAO implements IScaleDAO {


    @Override
    public void save(Scale entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void delete(Serializable id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Scale entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Scale findById(Serializable id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Scale> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
