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
import org.apache.log4j.Logger;
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

    private static final Logger LOG = Logger.getLogger(DefaultAuthService.class);

    @Autowired
    private IUserDAO userDAO;

    /**
     * Method to authenticate a user by username/email and password.
     * @param username The username (may not be null)
     * @param password The password (may not be null)
     * @return The status of authenticate indicated by {@code LoginStatus}
     */
    @Override
    public AuthStatus authenticate(java.lang.String username, java.lang.String password) {
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
    public boolean checkPassword(java.lang.String plaintext, java.lang.String hash) {
        Validate.notNull(plaintext);
        Validate.notNull(hash);

        return BCrypt.checkpw(plaintext, hash);
    }

    @Override
    public java.lang.String hashPassword(java.lang.String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    @Override
    public boolean isAuthorized(String userId, final Identifyable identifyable, final PermissionType permissionType) {
        User user = userDAO.findById(userId);

        if(user == null)
            return false;

        final List<Permission> permissions = new ArrayList<Permission>(user.getPermissions());

        if(permissions == null)
            return false;

        // Filter for given object type
        CollectionUtils.filter(permissions, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if (object instanceof Permission) {
                    return ((Permission) object).getObjectType().equals(PermissionObject.valueOf(identifyable.getClass()));
                }
                return false;
            }
        });

        // No permissions left. Not authorized!
        if(permissions.isEmpty()) {
            return false;
        }

        // Filter for given permission type
        CollectionUtils.filter(permissions, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if (object instanceof Permission) {
                    return ((Permission) object).getPermissionType().equals(permissionType);
                }
                return false;
            }
        });

        // No permissions left. Not authorized!
        if(permissions.isEmpty()) {
            return false;
        }

        for(Permission permission : permissions) {
            if(permission.hasObjectId() && identifyable.getId().equals(permission.getObjectId())) {
                return true;
            }
        }

        return false;
    }
}
