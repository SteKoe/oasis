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

import de.stekoe.idss.model.Company;

public interface CompanyRepository extends CrudRepository<Company, String> {
    /**
     * Finds any Company where the given user is employed.
     *
     * @param userId The userid to check
     * @return a list of Companys a user is employed.
     */
    @Query("SELECT c FROM Company c JOIN c.employees emp JOIN emp.user u WITH u.id = ?1)")
    List<Company> findByUser(String userId);
}
