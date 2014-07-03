package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.repository.ReferenceCriterionRepository;

@Service
@Transactional(readOnly = true)
public class ReferenceCriterionService {

    @Inject
    private ReferenceCriterionRepository referenceCriterionRepository;

    public List<Criterion> findAll() {
        return (List<Criterion>) referenceCriterionRepository.findAll();
    }

    public long count() {
        return referenceCriterionRepository.count();
    }

    public Page<Criterion> findAll(Pageable pageable) {
        return referenceCriterionRepository.findAll(pageable);
    }

    @Transactional
    public <S extends Criterion> S save(S entity) {
        return referenceCriterionRepository.save(entity);
    }

    @Transactional
    public <S extends Criterion> Iterable<S> save(Iterable<S> entities) {
        return referenceCriterionRepository.save(entities);
    }

    @Transactional
    public void delete(String id) {
        referenceCriterionRepository.delete(id);
    }
}
