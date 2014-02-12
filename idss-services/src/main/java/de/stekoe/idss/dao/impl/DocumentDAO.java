package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IDocumentDAO;
import de.stekoe.idss.model.Document;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class DocumentDAO  extends GenericDAO<Document> implements IDocumentDAO {
    @Override
    protected Class getPersistedClass() {
        return Document.class;
    }
}
