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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.repository.ProjectRoleRepository;

@Service
@Transactional(readOnly = true)
public class ProjectRoleService  {

    @Autowired
    ProjectRoleRepository projectRoleRepository;

    public ProjectRole findOne(String id) {
        return projectRoleRepository.findOne(id);
    }

    @Transactional
    public void save(ProjectRole role) {
        projectRoleRepository.save(role);
    }

    @Transactional
    public void delete(ProjectRole entity) {
        projectRoleRepository.delete(entity);
    }
}
