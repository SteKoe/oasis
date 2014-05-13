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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.SingleScaledCriterion;
import de.stekoe.idss.repository.CriterionRepository;

@Service
@Transactional(readOnly = true)
public class CriterionService {

    @Inject
    private CriterionRepository criterionRepository;

    public SingleScaledCriterion findSingleScaledCriterionById(String id) {
        return criterionRepository.findSingleScaledCriterionById(id);
    }

    @Transactional
    public void saveCriterion(Criterion entity) {
        criterionRepository.save(entity);
    }

    @Transactional
    public void deleteCriterion(String criterionId) {
        criterionRepository.delete(criterionId);
    }

    private void reorderValues(List<MeasurementValue> values) {
        int index = 0;
        final Iterator<MeasurementValue> iterator = values.iterator();
        while (iterator.hasNext()) {
            iterator.next().setOrdering(index++);
        }
    }

    public List<Criterion> findAllForProject(String id) {
        return criterionRepository.findAllForProject(id);
    }

    public Criterion findOne(String id) {
        return criterionRepository.findOne(id);
    }

}
