package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.CriterionPage;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionPageService extends DAOService<CriterionPage> {
    public enum Direction {
        UP,
        DOWN;
    };

    List<CriterionPage> getCriterionPagesForProject(String projectId);
    int getNextPageNumForProject(String projectId);
    void movePage(CriterionPage criterionPage, Direction up);
    CriterionPage findByOrdering(int ordering, String projectId);
}
