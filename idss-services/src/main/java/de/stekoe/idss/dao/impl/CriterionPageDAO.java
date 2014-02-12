package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CriterionPageDAO extends GenericDAO<CriterionPage> implements ICriterionPageDAO {
    @Override
    protected Class getPersistedClass() {
        return CriterionPage.class;
    }
}
