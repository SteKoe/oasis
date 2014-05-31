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

package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.repository.SystemRoleRepository;

@Service
@Transactional(readOnly = true)
public class SystemRoleService {

    @Inject
    SystemRoleRepository systemRoleRepository;

    public List<SystemRole> findAll() {
        return (List<SystemRole>) systemRoleRepository.findAll();
    }

    public SystemRole getAdminRole() {
        return systemRoleRepository.getRoleByName(SystemRole.ADMIN);
    }

    public SystemRole getUserRole() {
        return systemRoleRepository.getRoleByName(SystemRole.USER);
    }

    public SystemRole findById(String id) {
        return systemRoleRepository.findOne(id);
    }

    @Transactional
    public void save(SystemRole systemRole) {
        systemRoleRepository.save(systemRole);
    }

    @Transactional
    public void delete(SystemRole entity) {
        systemRoleRepository.delete(entity);
    }

    public SystemRole getRoleByName(String rolename) {
        return systemRoleRepository.getRoleByName(rolename);
    }

    public long count() {
        return systemRoleRepository.count();
    }
}