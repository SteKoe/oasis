package de.stekoe.oasis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.oasis.model.Criterion;
import de.stekoe.oasis.model.PageElement;
import de.stekoe.oasis.model.SingleScaledCriterion;

public interface CriterionRepository extends PagingAndSortingRepository<Criterion, String> {
    @Query("SELECT pe FROM PageElement pe WHERE pe.id = ?1")
    SingleScaledCriterion findSingleScaledCriterionById(String id);

    @Query("SELECT pe FROM CriterionPage cp JOIN cp.pageElements as pe WHERE cp.project.id = ?1 ORDER BY cp.ordering ASC")
    List<PageElement> findAllForProject(String id);
}
