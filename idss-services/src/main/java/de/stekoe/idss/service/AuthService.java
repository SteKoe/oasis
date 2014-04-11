/*
 * Copyright 2014 Stephan Koeninger Licensed under the Apache License, Version
 * 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package de.stekoe.idss.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.Validate;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public AuthStatus authenticate(String username, String password) {
        Validate.notBlank(username);
        Validate.notBlank(password);

        User user = userRepository.findByUsername(username);

        // User not found by username, try email instead
        if (user == null) {
            user = userRepository.findByEmail(username);
        }

        // User definitely not found.
        if (user == null) {
            return AuthStatus.ERROR;
        }

        // Check password
        if (!checkPassword(password, user.getPassword())) {
            return AuthStatus.ERROR;
        }

        // User is not activated
        if (UserStatus.ACTIVATION_PENDING.equals(user.getUserStatus())) {
            return AuthStatus.USER_NOT_ACTIVATED;
        }

        if(UserStatus.ACTIVATED.equals(user.getUserStatus()) == false) {
            if(UserStatus.RESET_PASSWORD.equals(user.getUserStatus()) == false) {
                return AuthStatus.ERROR;
            }
        }

        return AuthStatus.SUCCESS;
    }

    public boolean checkPassword(String plaintext, String hash) {
        Validate.notNull(plaintext);
        Validate.notNull(hash);

        return BCrypt.checkpw(plaintext, hash);
    }

    public String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    public boolean isAuthorized(String userId, final Identifyable identifyable, final PermissionType permissionType) {
        User user = userRepository.findOne(userId);

        if (user == null) {
            return false;
        }
        if (user.getPermissions() == null) {
            return false;
        }

        final List<Permission> permissions = new ArrayList<Permission>(
                user.getPermissions());
        final PermissionObject permissionObject = PermissionObject
                .valueOf(identifyable.getClass());
        permissionsFilter(permissions, permissionType, permissionObject);

        for (Permission permission : permissions) {
            if (permission.hasObjectId() && identifyable.getId().equals(permission.getObjectId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Filters out all permissions of aPermissionList which are not type of
     * aPermissionType or do not have aPermissionObject
     *
     * @param aPermissionList List of Permissions to filter
     * @param aPermissionType PermissionType which has to be part of Permission
     * @param aPermissionObject PermissionObject which has to be part of
     *            Permission
     */
    private void permissionsFilter(List<Permission> aPermissionList,
            final PermissionType aPermissionType,
            final PermissionObject aPermissionObject) {
        CollectionUtils.filter(aPermissionList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if (object instanceof Permission) {
                    final Permission permission = (Permission) object;
                    final boolean permissionObjectFits = permission
                            .getPermissionObject().equals(aPermissionObject);
                    final boolean permissionTypeFits = permission
                            .getPermissionType().equals(aPermissionType);
                    return permissionObjectFits && permissionTypeFits;
                }
                return false;
            }
        });
    }
}
