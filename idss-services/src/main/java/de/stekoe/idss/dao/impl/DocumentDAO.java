package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DocumentDAO  extends GenericDAO implements IDocumentDAO {
    @Override
    public void save(Document entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Serializable id) {
    }

    @Override
    public void delete(Document entity) {
    }

    @Override
    public Document findById(Serializable id) {
        return null;
    }

    @Override
    public List<Document> findAll() {
        return null;
    }
}
