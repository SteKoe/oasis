package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.repository.ReferenceCriterionGroupRepository;

@Service
@Transactional(readOnly = true)
public class ReferenceCriterionGroupService {

    @Inject
    private ReferenceCriterionGroupRepository referenceCriterionGroupRepository;

    @Transactional
    public <S extends CriterionGroup> S save(S entity) {
        return referenceCriterionGroupRepository.save(entity);
    }

    public Page<CriterionGroup> findAll(Pageable pageable) {
        return referenceCriterionGroupRepository.findAll(pageable);
    }

    public CriterionGroup findOne(String id) {
        return referenceCriterionGroupRepository.findOne(id);
    }

    public Iterable<CriterionGroup> findAll() {
        return referenceCriterionGroupRepository.findAll();
    }

    public long count() {
        return referenceCriterionGroupRepository.count();
    }

    @Transactional
    public void delete(String id) {
        referenceCriterionGroupRepository.delete(id);
    }
}
