package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.CriterionPage;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionPageService extends DAOService<CriterionPage> {
    List<CriterionPage> getCriterionPagesForProject(String projectId);
    int getNextPageNumForProject(String projectId);
}
