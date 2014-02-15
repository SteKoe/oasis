package de.stekoe.idss.dao;

import de.stekoe.idss.model.criterion.CriterionPage;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface ICriterionPageDAO extends IGenericDAO<CriterionPage> {
    List<CriterionPage> findAllForProject(String projectId);
    int getNextPageNumForProject(String projectId);
    CriterionPage findByOrdering(int ordering, String projectId);
}
