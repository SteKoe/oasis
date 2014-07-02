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

package de.stekoe.idss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.idss.model.CriterionPage;

public interface CriterionPageRepository extends PagingAndSortingRepository<CriterionPage, String> {
    /**
     * Find all {@link CriterionPage}s for a Project
     *
     * @param String Project id
     * @return A list of CirterionPages for the given Project id
     */
    @Query("FROM CriterionPage cp WHERE cp.project.id = ?1 ORDER BY cp.ordering")
    List<CriterionPage> findAllForProject(String String);

    @Query("SELECT COALESCE(MAX(cp.ordering), -1) FROM CriterionPage cp WHERE cp.project.id = ?1")
    int findMaxOrderingForProject(String projectId);

    @Query("FROM CriterionPage cp WHERE cp.project.id = ?1 AND cp.ordering = ?2")
    CriterionPage findOneByOrdering(String projectId, int ordering);
}
