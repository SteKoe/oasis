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

package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultSystemRoleService implements SystemRoleService {

    @Autowired
    ISystemRoleDAO systemRoleDAO;

    @Override
    public List<SystemRole> findAllRoles() {
        return systemRoleDAO.findAll();
    }

    @Override
    public SystemRole getAdminRole() {
        return systemRoleDAO.getRoleByName(SystemRole.ADMIN);
    }

    @Override
    public SystemRole getUserRole() {
        return systemRoleDAO.getRoleByName(SystemRole.USER);
    }

    @Override
    public SystemRole findById(String id) {
        return systemRoleDAO.findById(id);
    }

    @Override
    public void save(SystemRole systemRole) {
        systemRoleDAO.save(systemRole);
    }

    @Override
    public void delete(SystemRole entity) {
        systemRoleDAO.delete(entity);
    }
}