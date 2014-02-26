package de.stekoe.idss.dao;

import de.stekoe.idss.model.criterion.CriterionPage;

import java.util.List;

public interface ICriterionPageDAO extends IGenericDAO<CriterionPage> {
    /**
     * Find all {@link CriterionPage}s for a Project
     *
     * @param projectId Project id
     * @return A list of CirterionPages for the given Project id
     */
    List<CriterionPage> findAllForProject(String projectId);

    /**
     * Get the next page num for {@link CriterionPage}
     *
     * @param projectId Project id
     * @return Integer which represents the next (free) page number
     */
    int getNextPageNumForProject(String projectId);

    /**
     * Find a {@link CriterionPage} by ordering and Project
     *
     * @param ordering  The position of the page
     * @param projectId The id of the Project the page belongs to
     * @return The CriterionPage if found, null otherwise
     */
    CriterionPage findByOrdering(int ordering, String projectId);
}
