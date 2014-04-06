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
import org.springframework.data.repository.PagingAndSortingRepository;

import de.stekoe.idss.model.Project;

/**
 * Provides access to Project in database
 */
public interface ProjectRepository extends PagingAndSortingRepository<Project, String> {
    /**
     * Finds projects by its name.
     *
     * @param projectName The name of a project to lookup
     * @return A list of projects with the given name
     */
    List<Project> findByName(String projectName);

    /**
     * Finds projects by a given user id.
     * All projects a user is involved in, which means he/she is a project member of the project, will
     * be returned.
     *
     * @param userId The id of the user
     * @return A list of projects which the users is involved
     */
    @Query("SELECT p FROM Project p LEFT JOIN p.projectTeam pt LEFT JOIN pt.user u WITH u.id = ?1)")
    List<Project> findByUser(String userId);
}
