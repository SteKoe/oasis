/*
 * Copyright 2014 Stephan Köninger Licensed under the Apache License, Version
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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.model.criterion.CriterionPageElementId;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import de.stekoe.idss.repository.CriterionRepository;

@Service
@Transactional(readOnly = true)
public class CriterionService {

    @Inject
    private CriterionRepository criterionRepository;

    public SingleScaledCriterion findSingleScaledCriterionById(
            CriterionPageElementId id) {
        return criterionRepository.findSingleScaledCriterionById(id);
    }

    @Transactional
    public void saveCriterion(Criterion entity) {
        criterionRepository.save(entity);
    }

    @Transactional
    public void deleteCriterion(CriterionPageElementId criterionId) {
        criterionRepository.delete(criterionId);
    }

    public void deleteValue(MeasurementValue value) {
        final Scale scale = value.getScale();
        scale.getValues().remove(value);

        reorderValues(scale.getValues());

        final SingleScaledCriterion criterion = scale.getCriterion();
        saveCriterion(criterion);
    }

    private void reorderValues(List<MeasurementValue> values) {
        int index = 1;
        final Iterator<MeasurementValue> iterator = values.iterator();
        while (iterator.hasNext()) {
            iterator.next().setOrdering(index++);
        }
    }

    public void addValue(SingleScaledCriterion criterion, OrdinalValue value) {
        Scale scale = criterion.getScale();
        value.setRank(scale.getValues().size() + 1);
        value.setScale(scale);
        scale.getValues().add(value);

        criterion.setName(StringUtils.isBlank(criterion.getName()) ? ""
                : criterion.getName());

        saveCriterion(criterion);
    }
}
