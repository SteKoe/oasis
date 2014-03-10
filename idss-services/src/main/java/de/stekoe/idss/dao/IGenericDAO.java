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

package de.stekoe.idss.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IGenericDAO<T> {
    /**
     * Save the given entity to database
     *
     * @param entity
     */
    void save(T entity);

    /**
     * Delete the entity which is identified by the given id
     *
     * @param id
     */
    void delete(Serializable id);

    /**
     * Delete the given entity from database
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Find an entity by the given id
     *
     * @param id The id to lookup
     * @return The entity object or null
     */
    T findById(Serializable id);

    /**
     * Find all entities of the given type
     *
     * @return a list of entitites
     */
    List<T> findAll();
}
