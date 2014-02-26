package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.service.CriterionPageService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultCriterionPageService implements CriterionPageService {

    @Inject
    private ICriterionPageDAO criterionPageDAO;

    @Override
    public CriterionPage findById(String id) {
        return criterionPageDAO.findById(id);
    }

    @Override
    public void save(CriterionPage entity) {
        criterionPageDAO.save(entity);
    }

    @Override
    public void delete(CriterionPage entity) {
        final Project project = entity.getProject();
        criterionPageDAO.delete(entity);
        reorderPages(project);
    }

    private void reorderPages(Project aProject) {
        final List<CriterionPage> criterionPagesForProject = getCriterionPagesForProject(aProject.getId());
        for(int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            criterionPage.setOrdering(i+1);
            criterionPageDAO.save(criterionPage);
        }
    }

    @Override
    public List<CriterionPage> getCriterionPagesForProject(String projectId) {
        return criterionPageDAO.findAllForProject(projectId);
    }

    @Override
    public int getNextPageNumForProject(String projectId) {
        return criterionPageDAO.getNextPageNumForProject(projectId);
    }

    @Override
    public void movePage(CriterionPage criterionPage, Direction direction) {
        final int ordering = criterionPage.getOrdering();
        final Project project = criterionPage.getProject();

        int newOrdering = 0;
        if(Direction.UP.equals(direction)) {
            newOrdering = ordering - 1;
        } else if(Direction.DOWN.equals(direction)) {
            newOrdering = ordering + 1;
        }

        if(newOrdering == 0) {
            return;
        }

        final CriterionPage otherPage = findByOrdering(newOrdering, project.getId());

        criterionPage.setOrdering(newOrdering);
        criterionPageDAO.save(criterionPage);

        otherPage.setOrdering(ordering);
        criterionPageDAO.save(otherPage);
    }

    @Override
    public CriterionPage findByOrdering(int ordering, String projectId) {
        return criterionPageDAO.findByOrdering(ordering, projectId);
    }
}
