package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.service.DocumentService;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultDocumentService implements DocumentService {
    @Inject
    private IDocumentDAO documentDAO;

    @Override
    public void save(Document document) {
        documentDAO.save(document);
    }

    @Override
    public String getAbsolutePath(String id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
