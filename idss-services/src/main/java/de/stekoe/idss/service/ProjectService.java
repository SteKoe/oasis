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

import de.stekoe.idss.model.UserId;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ProjectService {

    /**
     * @param id The id of a Project to delete
     */
    void delete(ProjectId id);

    /**
     * @param project A project to save
     */
    void save(Project project);

    /**
     * @param id The id of a project to find
     * @return The project with the given id or null
     */
    Project findById(ProjectId id);

    /**
     * @param userId Id of a User to retrieve all projects he/she is involved
     * @return A list of Project
     */
    List<Project> findAllForUser(UserId userId);

    /**
     * @param userId Id of a User to retrieve all projects he/she is involved
     * @param perPage The number of entries per page
     * @param curPage The current page number
     * @return A list of Projects
     */
    List<Project> findAllForUserPaginated(UserId userId, int perPage, int curPage);

    /**
     * @param userId The id of a User to check permissions for
     * @param projectId The id of a Project to check permissions on
     * @param permissionType The PermissionType to be checked
     * @return true if a User is authorized to perform a given action, false otherwise
     */
    boolean isAuthorized(UserId userId, ProjectId projectId, PermissionType permissionType);
}
