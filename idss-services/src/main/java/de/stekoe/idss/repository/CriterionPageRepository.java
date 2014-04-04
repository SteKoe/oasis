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

package de.stekoe.idss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.CriterionPage;

public interface CriterionPageRepository extends CrudRepository<CriterionPage, String> {
    /**
     * Find all {@link CriterionPage}s for a Project
     *
     * @param String Project id
     * @return A list of CirterionPages for the given Project id
     */
    @Query("FROM CriterionPage cp WHERE cp.project.id = ?1 ORDER BY cp.ordering ASC")
    List<CriterionPage> findAllForProject(String String);

    /**
     * Get the next page num for {@link CriterionPage}
     *
     * @param String Project id
     * @return Integer which represents the next (free) page number
     */
    @Query("SELECT COALESCE(max(cp.ordering) + 1, 0) FROM CriterionPage cp LEFT JOIN cp.project p WHERE p.id = ?1")
    int getNextPageNumForProject(String String);

    /**
     * Find a {@link CriterionPage} by ordering and Project
     *
     * @param ordering  The position of the page
     * @param String The id of the Project the page belongs to
     * @return The CriterionPage if found, null otherwise
     */
    @Query("FROM CriterionPage cp LEFT JOIN cp.project p WHERE ordering = ?1 AND p.id = ?2")
    CriterionPage findByOrdering(int ordering, String String);
}
