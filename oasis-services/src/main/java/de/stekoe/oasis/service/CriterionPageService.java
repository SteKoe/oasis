package de.stekoe.oasis.service;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import de.stekoe.oasis.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.CriterionPage;
import de.stekoe.oasis.repository.CriterionPageRepository;

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
        CriterionPage criterionPage = findOne(criterionPageId);
        Project project = criterionPage.getProject();

        criterionPageRepository.delete(criterionPageId);

        String projectId = project.getId();
        reorderPages(projectId);
    }

    public List<CriterionPage> findAllForProject(String id, Pageable pageable) {
        return criterionPageRepository.findAllForProject(id, pageable);
    }

    public List<CriterionPage> findAll() {
        return (List<CriterionPage>) criterionPageRepository.findAll();
    }

    public void reorderPages(String projectId) {
        int ordering = 0;

        List<CriterionPage> pages = findAllForProject(projectId, null);
        Iterator<CriterionPage> pagesIterator = pages.iterator();
        while(pagesIterator.hasNext()) {
            CriterionPage page = pagesIterator.next();
            page.setOrdering(ordering);
            ordering++;
            save(page);
        }
    }

    public long countForProject(String pid) {
        return criterionPageRepository.countForProject(pid);
    }

    public CriterionPage findByProjectAndOrdering(Project project, int ordering) {
        return criterionPageRepository.findOneByOrdering(project.getId(), ordering);
    }

    public List<CriterionPage> findAllForProject(String id) {
        return criterionPageRepository.findAllForProject(id);
    }
}
