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

package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.CriterionPageElement;
import de.stekoe.idss.model.criterion.CriterionPageId;
import de.stekoe.idss.model.project.ProjectId;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionPageService extends Orderable<CriterionPage> {
    /**
     * @param id Id of CriterionPage
     * @return CriterionPage or null if no CriterionPage was found
     */
    CriterionPage findById(CriterionPageId id);

    /**
     * @param entity Entity to save
     */
    void save(CriterionPage entity);

    /**
     * @param entity The entity to delete
     */
    void delete(CriterionPage entity);

    /**
     * @param projectId Id of projct to load CriterionPages for
     * @return A list of CriterionPages
     */
    List<CriterionPage> getCriterionPagesForProject(ProjectId projectId);

    /**
     * @param projectId Id of project
     * @return The next page number
     */
    int getNextPageNumForProject(ProjectId projectId);

    /**
     * @param ordering  The position of the CriterionPage
     * @param projectId The id of Project the CriterionPage belongs to
     * @return The CriterionPage or null if none was found
     */
    CriterionPage findByOrdering(int ordering, ProjectId projectId);

    /**
     * @param aCriterionPage Reorder all elements on the given CriterionPage
     */
    void reorderPageElements(CriterionPage aCriterionPage);

    /**
     * @param aCriterionPageElement Deletes the given CriterionPageElement
     */
    void deletePageElement(CriterionPageElement aCriterionPageElement);
}
