package de.stekoe.idss.service;

import de.stekoe.idss.model.Document;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface DocumentService {
    /**
     * @param document Saves the given Document
     */
    void save(Document document);

    /**
     * @param id
     * @return
     */
    String getAbsolutePath(String id);

    /**
     * @param path
     */
    void setDocumentPath(String path);

    /**
     * @return
     */
    String getDocumentPath();
}
