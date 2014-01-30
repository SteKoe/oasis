package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.service.DocumentService;

import javax.inject.Inject;
import java.io.File;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultDocumentService implements DocumentService {
    @Inject
    private IDocumentDAO documentDAO;
    private String path;

    @Override
    public void save(Document document) {
        documentDAO.save(document);
    }

    @Override
    public String getAbsolutePath(String id) {
        final String[] idParts = id.split("-");
        return getDocumentPath() + File.separator + idParts[1] + File.separator + idParts[0] + ".data";
    }

    @Override
    public void setDocumentPath(String path) {
        this.path = path;
    }

    @Override
    public String getDocumentPath() {
        return path;
    }
}
