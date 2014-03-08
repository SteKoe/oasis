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

import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ScaleService {
    /**
     * @param value The value to move
     * @param direction Direction in which the value should be moved
     */
    void move(MeasurementValue value, Orderable.Direction direction);
}
