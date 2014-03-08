package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.CriterionPageElement;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionPageService extends Orderable<CriterionPage> {
    /**
     * @param id Id of CriterionPage
     * @return CriterionPage or null if no CriterionPage was found
     */
    CriterionPage findById(String id);

    /**
     * @param entity Entity to save
     */
    void save(CriterionPage entity);

    /**
     * @param entity The entity to delete
     */
    void delete(CriterionPage entity);

    /**
     * @param projectId Id of projct to load CriterionPages for
     * @return A list of CriterionPages
     */
    List<CriterionPage> getCriterionPagesForProject(String projectId);

    /**
     * @param projectId Id of project
     * @return The next page number
     */
    int getNextPageNumForProject(String projectId);

    /**
     * @param ordering  The position of the CriterionPage
     * @param projectId The id of Project the CriterionPage belongs to
     * @return The CriterionPage or null if none was found
     */
    CriterionPage findByOrdering(int ordering, String projectId);

    /**
     * @param aCriterionPage Reorder all elements on the given CriterionPage
     */
    void reorderPageElements(CriterionPage aCriterionPage);

    /**
     * @param aCriterionPageElement Deletes the given CriterionPageElement
     */
    void deletePageElement(CriterionPageElement aCriterionPageElement);
}
