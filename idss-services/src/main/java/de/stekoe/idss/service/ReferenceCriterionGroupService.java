/*
 * Copyright 2014 Stephan Koeninger Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package de.stekoe.idss.service;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.repository.ReferenceCriterionGroupRepository;

@Service
@Transactional(readOnly = true)
public class ReferenceCriterionGroupService {

    @Inject
    private ReferenceCriterionGroupRepository referenceCriterionGroupRepository;

    public Iterable<CriterionGroup> findAll() {
        return referenceCriterionGroupRepository.findAll();
    }

    public long count() {
        return referenceCriterionGroupRepository.count();
    }

    public Iterable<CriterionGroup> findAll(Sort sort) {
        return referenceCriterionGroupRepository.findAll(sort);
    }

    public Page<CriterionGroup> findAll(Pageable pageable) {
        return referenceCriterionGroupRepository.findAll(pageable);
    }
}
