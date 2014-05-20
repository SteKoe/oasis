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
import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.UserChoice;

public interface UserChoiceRepository extends CrudRepository<UserChoice, String> {
    @Query("SELECT uc FROM UserChoice uc WHERE uc.criterion.id = ?2 AND uc.user.id = ?1")
    UserChoice findByUserAndCriterion(String userId, String criterionId);

    @Query("SELECT uc FROM UserChoice uc WHERE uc.criterion.id = ?2 AND uc.project.id = ?1")
    UserChoice findByProjectAndCriterion(String projectId, String criterionId);

    @Query("SELECT uc FROM UserChoice uc JOIN uc.measurementValues mv WITH mv.id = ?2")
    List<UserChoice> findByMeasurementValue(String id);

    @Query("SELECT DISTINCT uc FROM UserChoice uc WHERE uc.criterion.id = ?1")
    List<UserChoice> findByCriterionId(String criterionId);
}
