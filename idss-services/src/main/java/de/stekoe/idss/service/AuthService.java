package de.stekoe.idss.service;

import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.enums.PermissionType;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface AuthService {
    /**
     * Method to authenticate a user by username/email and password.
     *
     * @param username The username (may not be null)
     * @param password The password (may not be null)
     * @return The status of authenticate indicated by {@code LoginStatus}
     */
    AuthStatus authenticate(String username, String password);

    /**
     * @param plaintext
     * @param hash
     * @return
     */
    boolean checkPassword(String plaintext, String hash);

    /**
     * @param plaintext
     * @return
     */
    String hashPassword(String plaintext);

    /**
     * @param userId
     * @param identifyable
     * @param permissionType
     * @return
     */
    boolean isAuthorized(String userId, Identifyable identifyable, PermissionType permissionType);
}
