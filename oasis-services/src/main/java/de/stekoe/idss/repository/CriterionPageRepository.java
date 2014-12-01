package de.stekoe.idss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.idss.model.CriterionPage;

public interface CriterionPageRepository extends PagingAndSortingRepository<CriterionPage, String> {
    /**
     * Find all {@link CriterionPage}s for a Project
     *
     * @param String Project id
     * @return A list of CirterionPages for the given Project id
     */
    @Query("FROM CriterionPage cp WHERE cp.project.id = ?1 ORDER BY cp.ordering")
    List<CriterionPage> findAllForProject(String String, Pageable pageable);

    @Query("SELECT COALESCE(MAX(cp.ordering), -1) FROM CriterionPage cp WHERE cp.project.id = ?1")
    int findMaxOrderingForProject(String projectId);

    @Query("FROM CriterionPage cp WHERE cp.project.id = ?1 AND cp.ordering = ?2")
    CriterionPage findOneByOrdering(String projectId, int ordering);

    @Query("SELECT COUNT(cp.id) FROM CriterionPage cp WHERE cp.project.id = ?1")
    long countForProject(String projectId);
}
