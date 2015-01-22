package de.stekoe.oasis.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.oasis.model.Criterion;

public interface ReferenceCriterionRepository extends PagingAndSortingRepository<Criterion, String> {
    @Override
    @Query("FROM Criterion c WHERE c.referenceType = 1")
    public Iterable<Criterion> findAll();

    @Override
    @Query("FROM Criterion c WHERE c.referenceType = 1")
    public Page<Criterion> findAll(Pageable pageable);

    @Override
    @Query("SELECT COUNT(*) FROM Criterion c WHERE c.referenceType = 1")
    public long count();
}
