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

import de.stekoe.idss.model.SystemRole;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface SystemRoleService {

    /**
     * @return List of all SystemRoles
     */
    List<SystemRole> findAllRoles();

    /**
     * @return The SystemRole for administrators
     */
    SystemRole getAdminRole();

    /**
     * @return The SystemRole for registered Users
     */
    SystemRole getUserRole();

    /**
     * @param entity SystemRole to delete
     */
    void delete(SystemRole entity);

    /**
     * @param systemRole SystemRole to save
     */
    void save(SystemRole systemRole);

    /**
     * @param id Id of the SystemRole to retrieve
     * @return The SystemRole if found or null
     */
    SystemRole findById(String id);
}
