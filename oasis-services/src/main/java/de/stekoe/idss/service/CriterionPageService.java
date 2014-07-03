package de.stekoe.idss.service;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrderableUtil.Direction;
import de.stekoe.idss.repository.CriterionPageRepository;

@Service
@Transactional(readOnly = true)
public class CriterionPageService {

    @Inject
    private CriterionPageRepository criterionPageRepository;

    public CriterionPage findOne(String id) {
        return criterionPageRepository.findOne(id);
    }

    @Transactional
    public void save(CriterionPage entity) {
        if(entity.getOrdering() < 0) {
            int ordering = criterionPageRepository.findMaxOrderingForProject(entity.getProject().getId());
            entity.setOrdering(ordering + 1);
        }
        criterionPageRepository.save(entity);
    }

    @Transactional
    public void delete(String criterionPageId) {
        String projectId = findOne(criterionPageId).getProject().getId();
        criterionPageRepository.delete(criterionPageId);
        reorderPages(projectId);
    }

    public List<CriterionPage> getCriterionPagesForProject(String projectId) {
        return criterionPageRepository.findAllForProject(projectId);
    }

    public List<CriterionPage> findAllForProject(String id) {
        return criterionPageRepository.findAllForProject(id);
    }

    public List<CriterionPage> findAll() {
        return (List<CriterionPage>) criterionPageRepository.findAll();
    }

    public void reorderPages(String projectId) {
        int ordering = 0;

        List<CriterionPage> pages = findAllForProject(projectId);
        Iterator<CriterionPage> pagesIterator = pages.iterator();
        while(pagesIterator.hasNext()) {
            CriterionPage page = pagesIterator.next();
            page.setOrdering(ordering);
            ordering++;
            save(page);
        }
    }

    @Transactional
    public void move(CriterionPage criterionPage, Direction direction) {
        if(Direction.UP.equals(direction)) {
            moveUp(criterionPage, 1);
        } else {
            moveDown(criterionPage, 1);
        }
    }

    /**
     * @param criterionPage
     * @param delta
     *
     * A 0
     * B 1
     */
    @Transactional
    public void moveUp(CriterionPage criterionPage, int delta) {
        int pageIndex = criterionPage.getOrdering();
        int newPageIndex = pageIndex - delta;

        if(newPageIndex >= 0) {
            CriterionPage otherPage = criterionPageRepository.findOneByOrdering(criterionPage.getProject().getId(), newPageIndex);
            criterionPage.setOrdering(newPageIndex);
            otherPage.setOrdering(pageIndex);

            criterionPageRepository.save(criterionPage);
            criterionPageRepository.save(otherPage);
        }
    }

    @Transactional
    public void moveDown(CriterionPage criterionPage, int delta) {
        int pageIndex = criterionPage.getOrdering();
        int newPageIndex = pageIndex + delta;

        if(newPageIndex <= criterionPageRepository.findMaxOrderingForProject(criterionPage.getProject().getId())) {
            CriterionPage otherPage = criterionPageRepository.findOneByOrdering(criterionPage.getProject().getId(), newPageIndex);
            criterionPage.setOrdering(newPageIndex);
            otherPage.setOrdering(pageIndex);

            criterionPageRepository.save(criterionPage);
            criterionPageRepository.save(otherPage);
        }
    }
}
