package de.stekoe.oasis.service;

import de.stekoe.oasis.model.*;
import de.stekoe.oasis.repository.CriterionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CriterionService extends PageElementService {

    @Inject
    private CriterionRepository criterionRepository;

    @Inject
    private CriterionGroupService criterionGroupService;

    @Inject
    private CriterionPageService criterionPageService;


    public SingleScaledCriterion findSingleScaledCriterionById(String id) {
        return criterionRepository.findSingleScaledCriterionById(id);
    }

    @Transactional
    public void save(Criterion entity) {
        criterionRepository.save(entity);
    }

    @Transactional
    public void delete(String criterionId) {
        Criterion one = criterionRepository.findOne(criterionId);
        CriterionGroup criterionGroup = one.getCriterionGroup();
        CriterionPage criterionPage = one.getCriterionPage();
        reorder(criterionPage, criterionGroup);

        criterionRepository.delete(criterionId);
    }

    private void reorder(CriterionPage criterionPage, CriterionGroup criterionGroup) {
        if(criterionPage != null) {
            criterionPageService.save(criterionPage);
        }
        if(criterionGroup != null) {
            criterionGroupService.save(criterionGroup);
        }
    }

    public List<Criterion> findAllForReport(String projectId) {
        List<PageElement> findAllForProject = new ArrayList<>(criterionRepository.findAllForProject(projectId));

        List<Criterion> criterions = new ArrayList<>();

        for (PageElement criterion : findAllForProject) {
            if(criterion instanceof Criterion) {
                criterions.add((Criterion) criterion);
            } else if(criterion instanceof CriterionGroup) {
                CriterionGroup cg = (CriterionGroup) criterion;
                for (Criterion c : cg.getCriterions()) {
                    criterions.add(c);
                }
            }
        }

        return criterions;
    }

    public Criterion findOne(String id) {
        return criterionRepository.findOne(id);
    }

    public long count() {
        return criterionRepository.count();
    }

    public List<Criterion> findAll() {
        return (List<Criterion>) criterionRepository.findAll();
    }

    @Transactional
    public <S extends Criterion> Iterable<S> save(Iterable<S> entities) {
        return criterionRepository.save(entities);
    }
}