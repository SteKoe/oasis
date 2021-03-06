package de.stekoe.idss.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.repository.CriterionRepository;
import de.stekoe.idss.repository.UserChoiceRepository;

@Service
@Transactional
public class CriterionService extends PageElementService {

    @Inject
    private CriterionRepository criterionRepository;

    @Inject
    private UserChoiceRepository userChoiceRepository;

    public SingleScaledCriterion findSingleScaledCriterionById(String id) {
        return criterionRepository.findSingleScaledCriterionById(id);
    }

    @Transactional
    public void save(Criterion entity) {
        criterionRepository.save(entity);
    }

    @Transactional
    public void delete(String criterionId) {
        List<UserChoice> userChoices = userChoiceRepository.findByCriterionId(criterionId);
        userChoiceRepository.delete(userChoices);
        criterionRepository.delete(criterionId);
    }

    public List<Criterion> findAllForReport(String id) {
        List<PageElement> findAllForProject = new ArrayList<PageElement>(criterionRepository.findAllForProject(id));

        List<Criterion> criterions = new ArrayList<Criterion>();

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
