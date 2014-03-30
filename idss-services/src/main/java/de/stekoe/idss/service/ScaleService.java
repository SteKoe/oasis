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

import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.criterion.scale.ScaleId;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.repository.MeasurementValueRepository;

@Transactional(readOnly = true)
public class ScaleService implements Orderable<MeasurementValue> {

    @Inject
    private MeasurementValueRepository measurementValueRepository;

    @Override
    public void move(MeasurementValue value, Orderable.Direction direction) {
        final int ordering = value.getOrdering();
        final Scale scale = value.getScale();

        int newOrdering = 0;
        if (Orderable.Direction.UP.equals(direction)) {
            newOrdering = ordering - 1;
        } else if (Orderable.Direction.DOWN.equals(direction)) {
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

    private MeasurementValue findByOrdering(int newOrdering, ScaleId scaleId) {
        return measurementValueRepository.findByOrdering(newOrdering, scaleId);
    }
}
