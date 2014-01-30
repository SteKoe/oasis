package de.stekoe.idss.service;

import de.stekoe.idss.model.Document;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface DocumentService {
    void save(Document document);
    String getAbsolutePath(String id);
}
