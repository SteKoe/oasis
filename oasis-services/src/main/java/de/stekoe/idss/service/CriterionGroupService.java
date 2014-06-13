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

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.repository.CriterionGroupRepository;

@Service
@Transactional(readOnly = true)
public class CriterionGroupService extends PageElementService {

    @Inject
    private CriterionGroupRepository criterionGroupRepository;

    public Page<CriterionGroup> findAll(Pageable pageable) {
        return criterionGroupRepository.findAll(pageable);
    }

    public CriterionGroup findOne(String id) {
        return criterionGroupRepository.findOne(id);
    }

    public Iterable<CriterionGroup> findAll() {
        return criterionGroupRepository.findAll();
    }

    public void delete(CriterionGroup entity) {
        delete(entity.getId());
    }

    @Transactional
    public void delete(String id) {
        CriterionGroup cg = findOne(id);
        if(cg != null) {
            if(!cg.isReferenceType()) {
                List<Criterion> criterions = cg.getCriterions();
                Iterator<Criterion> iterator = criterions.iterator();
                while(iterator.hasNext()) {
                    iterator.remove();
                }
            }
            criterionGroupRepository.delete(id);
        }
    }

    @Transactional
    public <S extends CriterionGroup> S save(S entity) {
        return criterionGroupRepository.save(entity);
    }

    public long count() {
        return criterionGroupRepository.count();
    }

    @Transactional
    public <S extends CriterionGroup> Iterable<S> save(Iterable<S> entities) {
        return criterionGroupRepository.save(entities);
    }
}
