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

package de.stekoe.idss.dao;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.project.ProjectId;

import java.util.List;

public interface ICriterionPageDAO extends IGenericDAO<CriterionPage> {
    /**
     * Find all {@link CriterionPage}s for a Project
     *
     * @param projectId Project id
     * @return A list of CirterionPages for the given Project id
     */
    List<CriterionPage> findAllForProject(ProjectId projectId);

    /**
     * Get the next page num for {@link CriterionPage}
     *
     * @param projectId Project id
     * @return Integer which represents the next (free) page number
     */
    int getNextPageNumForProject(ProjectId projectId);

    /**
     * Find a {@link CriterionPage} by ordering and Project
     *
     * @param ordering  The position of the page
     * @param projectId The id of the Project the page belongs to
     * @return The CriterionPage if found, null otherwise
     */
    CriterionPage findByOrdering(int ordering, ProjectId projectId);

    CriterionPage findPageOfCriterionElement(SingleScaledCriterion aCriterion);
}
