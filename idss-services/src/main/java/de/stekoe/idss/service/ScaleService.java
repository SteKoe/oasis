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

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.criterion.CriterionPageElementId;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.repository.MeasurementValueRepository;

@Service
@Transactional(readOnly = true)
public class ScaleService {

    @Inject
    private MeasurementValueRepository measurementValueRepository;

    public void move(MeasurementValue value, Direction direction) {
        final int ordering = value.getOrdering();
        final SingleScaledCriterion scale = value.getScale();

        int newOrdering = 0;
        if (Direction.UP.equals(direction)) {
            newOrdering = ordering - 1;
        } else if (Direction.DOWN.equals(direction)) {
            newOrdering = ordering + 1;
        }

        if (newOrdering == 0) {
            return;
        }

        final MeasurementValue other = findByOrdering(newOrdering, scale.getId());

        value.setOrdering(newOrdering);
        measurementValueRepository.save(value);

        other.setOrdering(ordering);
        measurementValueRepository.save(other);
    }

    private MeasurementValue findByOrdering(int newOrdering, CriterionPageElementId scaleId) {
        return measurementValueRepository.findByOrdering(newOrdering, scaleId);
    }
}
