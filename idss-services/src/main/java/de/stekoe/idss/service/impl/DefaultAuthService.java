package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.Validate;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultAuthService implements AuthService {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public AuthStatus authenticate(String username, String password) {
        Validate.notBlank(username);
        Validate.notBlank(password);

        User user = userDAO.findByUsername(username);

        // User not found by username, try email instead
        if (user == null) {
            user = userDAO.findByEmail(username);
        }

        // User definitely not found.
        if (user == null) {
            return AuthStatus.USER_NOT_FOUND;
        }

        // Check password
        if (!checkPassword(password, user.getPassword())) {
            return AuthStatus.WRONG_PASSWORD;
        }

        // User is not activated
        if (UserStatus.ACTIVATION_PENDING.equals(user.getUserStatus())) {
            return AuthStatus.USER_NOT_ACTIVATED;
        }

        return AuthStatus.SUCCESS;
    }

    @Override
    public boolean checkPassword(String plaintext, String hash) {
        Validate.notNull(plaintext);
        Validate.notNull(hash);

        return BCrypt.checkpw(plaintext, hash);
    }

    @Override
    public String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    @Override
    public boolean isAuthorized(String userId, final Identifyable identifyable, final PermissionType permissionType) {
        User user = userDAO.findById(userId);

        if (user == null) {
            return false;
        }
        if (user.getPermissions() == null) {
            return false;
        }

        final List<Permission> permissions = new ArrayList<Permission>(user.getPermissions());
        final PermissionObject permissionObject = PermissionObject.valueOf(identifyable.getClass());
        permissionsFilter(permissions, permissionType, permissionObject);

        for (Permission permission : permissions) {
            if (permission.hasObjectId() && identifyable.getId().equals(permission.getObjectId())) {
                return true;
            }
        }

        return false;
    }

    /**
     * Filters out all permissions of aPermissionList which are not type of aPermissionType or do not have aPermissionObject
     *
     * @param aPermissionList List of Permissions to filter
     * @param aPermissionType PermissionType which has to be part of Permission
     * @param aPermissionObject PermissionObject which has to be part of Permission
     */
    private void permissionsFilter(List<Permission> aPermissionList, final PermissionType aPermissionType, final PermissionObject aPermissionObject) {
        CollectionUtils.filter(aPermissionList, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if (object instanceof Permission) {
                    final Permission permission = (Permission) object;
                    final boolean permissionObjectFits = permission.getObjectType().equals(aPermissionObject);
                    final boolean permissionTypeFits = permission.getPermissionType().equals(aPermissionType);
                    return permissionObjectFits && permissionTypeFits;
                }
                return false;
            }
        });
    }
}
