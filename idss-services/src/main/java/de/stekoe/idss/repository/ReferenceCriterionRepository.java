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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.idss.model.Criterion;

public interface ReferenceCriterionRepository extends PagingAndSortingRepository<Criterion, String> {
    @Override
    @Query("FROM Criterion c WHERE c.referenceType = 1")
    public Iterable<Criterion> findAll();

    @Override
    @Query("FROM Criterion c WHERE c.referenceType = 1")
    public Page<Criterion> findAll(Pageable pageable);

    @Override
    @Query("SELECT COUNT(*) FROM Criterion c WHERE c.referenceType = 1")
    public long count();
}
