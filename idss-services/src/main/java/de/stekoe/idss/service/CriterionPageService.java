/*
 * Copyright 2014 Stephan Koeninger
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

package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.Project;
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
        if (entity.getOrdering() < 0) {
            String id = entity.getProject().getId();
            int nextPageNumForProject = getNextPageNumForProject(id);
            entity.setOrdering(nextPageNumForProject);
        }
        criterionPageRepository.save(entity);
    }

    @Transactional
    public void delete(String criterionPageId) {
        final Project project = findOne(criterionPageId).getProject();
        criterionPageRepository.delete(criterionPageId);
        reorderPages(project);
    }

    @Transactional
    private void reorderPages(Project aProject) {
        final List<CriterionPage> criterionPagesForProject = getCriterionPagesForProject(aProject.getId());
        for (int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            criterionPage.setOrdering(i);
            criterionPageRepository.save(criterionPage);
        }
    }

    public List<CriterionPage> getCriterionPagesForProject(String projectId) {
        return criterionPageRepository.findAllForProject(projectId);
    }

    public int getNextPageNumForProject(String projectId) {
        return criterionPageRepository.getNextPageNumForProject(projectId);
    }

    @Transactional
    public void move(CriterionPage criterionPage, Direction direction) {
        final int ordering = criterionPage.getOrdering();
        final Project project = criterionPage.getProject();

        int newOrdering = 0;
        if (Direction.UP.equals(direction)) {
            newOrdering = ordering - 1;
        } else if (Direction.DOWN.equals(direction)) {
            newOrdering = ordering + 1;
        }

        if (newOrdering < 0) {
            return;
        }

        final CriterionPage otherPage = findByOrdering(newOrdering, project.getId());

        criterionPage.setOrdering(newOrdering);
        criterionPageRepository.save(criterionPage);

        otherPage.setOrdering(ordering);
        criterionPageRepository.save(otherPage);
    }

    public CriterionPage findByOrdering(int ordering, String projectId) {
        return criterionPageRepository.findByOrdering(ordering, projectId);
    }

    @Transactional
    public void reorderPageElements(CriterionPage aCriterionPage) {
        final List<PageElement> pageElements = aCriterionPage.getPageElements();
        for(int i = 0; i < pageElements.size(); i++) {
            pageElements.get(0).setOrdering(i + 1);
        }
        criterionPageRepository.save(aCriterionPage);
    }

    public List<CriterionPage> findAllForProject(String id) {
        return criterionPageRepository.findAllForProject(id);
    }

    public List<CriterionPage> findAll() {
        return (List<CriterionPage>) criterionPageRepository.findAll();
    }
}
