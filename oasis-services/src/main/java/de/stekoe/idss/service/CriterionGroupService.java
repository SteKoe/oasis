package de.stekoe.idss.service;

import javax.inject.Inject;

import de.stekoe.idss.model.Criterion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.repository.CriterionGroupRepository;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CriterionGroupService extends PageElementService {

    @Inject
    private CriterionGroupRepository criterionGroupRepository;

    @Inject
    private CriterionService criterionService;

    public Page<CriterionGroup> findAll(Pageable pageable) {
        return criterionGroupRepository.findAll(pageable);
    }

    public CriterionGroup findOne(String id) {
        return criterionGroupRepository.findOne(id);
    }

    public Iterable<CriterionGroup> findAll() {
        return criterionGroupRepository.findAll();
    }

    public void delete(CriterionGroup entity) {
        delete(entity.getId());
    }

    @Transactional
    public void delete(String id) {
        criterionGroupRepository.delete(id);
    }

    @Transactional
    public <S extends CriterionGroup> S save(S entity) {
        return criterionGroupRepository.save(entity);
    }

    public long count() {
        return criterionGroupRepository.count();
    }

    @Transactional
    public <S extends CriterionGroup> Iterable<S> save(Iterable<S> entities) {
        return criterionGroupRepository.save(entities);
    }
}
