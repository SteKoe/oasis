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

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.model.SingleScaledCriterion;

public interface CriterionRepository extends PagingAndSortingRepository<Criterion, String> {
    @Query("SELECT pe FROM PageElement pe WHERE pe.id = ?1")
    SingleScaledCriterion findSingleScaledCriterionById(String id);

    @Query("SELECT pe FROM CriterionPage cp JOIN cp.pageElements as pe WHERE cp.project.id = ?1")
    List<PageElement> findAllForProject(String id);
}
