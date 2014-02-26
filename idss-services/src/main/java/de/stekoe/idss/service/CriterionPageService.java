package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.CriterionPage;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionPageService {
    /**
     * When moving pages, this directions are supported
     */
    public enum Direction {
        UP,
        DOWN;
    }

    ;

    /**
     * Finds a {@link CriterionPage} by the given id
     *
     * @param id Id of CriterionPage
     * @return CriterionPage or null if no CriterionPage was found
     */
    CriterionPage findById(String id);

    /**
     * Saves a {@link CriterionPage} to database
     *
     * @param entity Entity to save
     */
    void save(CriterionPage entity);

    /**
     * Deletes the given {@link CriterionPage} from database
     *
     * @param entity The entity to delete
     */
    void delete(CriterionPage entity);

    /**
     * Retrieves a list of {@link CriterionPage}s for the given project id from database.
     *
     * @param projectId Id of projct to load CriterionPages for
     * @return A list of CriterionPages
     */
    List<CriterionPage> getCriterionPagesForProject(String projectId);

    /**
     * Calculates the next page number for a project.
     *
     * @param projectId Id of project
     * @return The next page number
     */
    int getNextPageNumForProject(String projectId);

    /**
     * Moves a {@link CriterionPage} by given {@link Direction}
     *
     * @param criterionPage The CriterionPage to move
     * @param up            The Direction to move
     */
    void movePage(CriterionPage criterionPage, Direction up);

    /**
     * Finds a {@link CriterionPage} by it's order and Project.
     *
     * @param ordering  The position of the CriterionPage
     * @param projectId The id of Project the CriterionPage belongs to
     * @return The CriterionPage or null if none was found
     */
    CriterionPage findByOrdering(int ordering, String projectId);
}
