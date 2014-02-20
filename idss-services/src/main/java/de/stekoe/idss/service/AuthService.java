package de.stekoe.idss.service;

import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.enums.PermissionType;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface AuthService {
    AuthStatus authenticate(String username, String password);
    boolean checkPassword(String plaintext, String hash);
    String hashPassword(String plaintext);
    boolean isAuthorized(String userId, Identifyable identifyable, PermissionType permissionType);
}
