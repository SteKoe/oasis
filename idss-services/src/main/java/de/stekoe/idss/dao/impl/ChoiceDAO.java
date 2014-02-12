package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IChoiceDAO;
import de.stekoe.idss.model.scale.Choice;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class ChoiceDAO extends GenericDAO implements IChoiceDAO {
    @Override
    public void save(Choice entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void delete(Serializable id) {
    }

    @Override
    public void delete(Choice entity) {
    }

    @Override
    public Choice findById(Serializable id) {
        return null;
    }

    @Override
    public List<Choice> findAll() {
        return null;
    }
}
