/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.CriterionPageElement;
import de.stekoe.idss.model.criterion.CriterionPageId;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;
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
    public CriterionPage findById(CriterionPageId id) {
        return criterionPageDAO.findById(id);
    }

    @Override
    public void save(CriterionPage entity) {
        if (entity.getOrdering() < 0) {
            entity.setOrdering(getNextPageNumForProject(entity.getProject().getId()));
        }
        criterionPageDAO.save(entity);
    }

    @Override
    public void delete(CriterionPageId criterionPageId) {
        final Project project = findById(criterionPageId).getProject();
        criterionPageDAO.delete(criterionPageId);
        reorderPages(project);
    }

    private void reorderPages(Project aProject) {
        final List<CriterionPage> criterionPagesForProject = getCriterionPagesForProject(aProject.getId());
        for (int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            criterionPage.setOrdering(i + 1);
            criterionPageDAO.save(criterionPage);
        }
    }

    @Override
    public List<CriterionPage> getCriterionPagesForProject(ProjectId projectId) {
        return criterionPageDAO.findAllForProject(projectId);
    }

    @Override
    public int getNextPageNumForProject(ProjectId projectId) {
        return criterionPageDAO.getNextPageNumForProject(projectId);
    }

    @Override
    public void move(CriterionPage criterionPage, Direction direction) {
        final int ordering = criterionPage.getOrdering();
        final Project project = criterionPage.getProject();

        int newOrdering = 0;
        if (Direction.UP.equals(direction)) {
            newOrdering = ordering - 1;
        } else if (Direction.DOWN.equals(direction)) {
            newOrdering = ordering + 1;
        }

        if (newOrdering == 0) {
            return;
        }

        final CriterionPage otherPage = findByOrdering(newOrdering, project.getId());

        criterionPage.setOrdering(newOrdering);
        criterionPageDAO.save(criterionPage);

        otherPage.setOrdering(ordering);
        criterionPageDAO.save(otherPage);
    }

    @Override
    public CriterionPage findByOrdering(int ordering, ProjectId projectId) {
        return criterionPageDAO.findByOrdering(ordering, projectId);
    }

    @Override
    public void reorderPageElements(CriterionPage aCriterionPage) {
        final List<CriterionPageElement> pageElements = aCriterionPage.getPageElements();
        for(int i = 0; i < pageElements.size(); i++) {
            pageElements.get(0).setOrdering(i + 1);
        }
        criterionPageDAO.save(aCriterionPage);
    }

    @Override
    public void deletePageElement(CriterionPageElement aCriterionPageElement) {
    }
}
