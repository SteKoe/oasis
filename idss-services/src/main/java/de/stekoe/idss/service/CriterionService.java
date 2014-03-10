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

import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.model.criterion.CriterionPageElementId;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionService {
    /**
     * @param entity
     */
    void saveCriterion(Criterion entity);

    /**
     * @param id
     * @return
     */
    Criterion findById(CriterionPageElementId id);

    /**
     * @param criterionId
     */
    void deleteCriterion(CriterionPageElementId criterionId);

    /**
     * @param value The value to delete from Criterion
     */
    void deleteValue(MeasurementValue value);
}
