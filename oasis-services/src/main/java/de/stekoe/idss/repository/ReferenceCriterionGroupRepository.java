package de.stekoe.idss.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import de.stekoe.idss.model.CriterionGroup;

public interface ReferenceCriterionGroupRepository extends CriterionGroupRepository {
    @Override
    @Query("FROM CriterionGroup cg WHERE cg.referenceType = 1")
    public Iterable<CriterionGroup> findAll();

    @Override
    @Query("FROM CriterionGroup cg WHERE cg.referenceType = 1")
    public Page<CriterionGroup> findAll(Pageable pageable);

    @Override
    @Query("SELECT COUNT(*) FROM CriterionGroup cg WHERE cg.referenceType = 1")
    public long count();
}
