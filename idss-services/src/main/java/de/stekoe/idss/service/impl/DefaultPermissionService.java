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

import de.stekoe.idss.dao.impl.PermissionDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultPermissionService implements PermissionService {

    @Autowired
    PermissionDAO permissionDAO;

    @Override
    public void save(Permission permission) {
        permissionDAO.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        permissionDAO.delete(permission);
    }
}
