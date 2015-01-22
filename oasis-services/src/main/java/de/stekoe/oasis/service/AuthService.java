package de.stekoe.oasis.service;

import de.stekoe.oasis.model.*;
import de.stekoe.oasis.repository.UserRepository;
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

@Service
@Transactional(readOnly = true)
public class AuthService {

    private static final Logger LOG = Logger.getLogger(AuthService.class);

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
        if(userId == null || identifyable == null) {
            return false;
        }

        User user = userRepository.findOne(userId);
        if (user == null) {
            return false;
        }
        if(user.getRoles().contains(new SystemRole(SystemRole.ADMIN))) {
            return true;
        }
        if (user.getPermissions() == null) {
            return false;
        }

        List<Permission> permissions = new ArrayList<Permission>(user.getPermissions());

        final PermissionObject permissionObject = PermissionObject.valueOf(identifyable.getClass());
        permissions = permissionsFilter(permissions, permissionType, permissionObject);

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
     * @param listOfPermissions List of Permissions to filter
     * @param permissionType PermissionType which has to be part of Permission
     * @param permissionObject PermissionObject which has to be part of
     *            Permission
     *
     * @return A List of Permissions
     */
    List<Permission> permissionsFilter(List<Permission> listOfPermissions, final PermissionType permissionType, final PermissionObject permissionObject) {

//        The Java 8 way :)
//        return listOfPermissions
//            .stream()
//            .filter(p -> p.getPermissionObject().equals(permissionObject) || PermissionType.ALL.equals(p.getPermissionType()))
//            .filter(p -> p.getPermissionType().equals(permissionType))
//            .collect(Collectors.toList());

        CollectionUtils.filter(listOfPermissions, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                if (object instanceof Permission) {
                    final Permission permission = (Permission) object;
                    final boolean permissionTypeFits = permission.getPermissionType().equals(permissionType) || PermissionType.ALL.equals(permission.getPermissionType());
                    final boolean permissionObjectFits = permission.getPermissionObject().equals(permissionObject);
                    return permissionObjectFits && permissionTypeFits;
                }
                return false;
            }
        });
        return listOfPermissions;
    }
}
