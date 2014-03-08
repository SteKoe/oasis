package de.stekoe.idss.service;

import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.enums.PermissionType;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface AuthService {
    /**
     * @param username The username (may not be null)
     * @param password The password (may not be null)
     * @return The status of authenticate indicated by {@code LoginStatus}
     */
    AuthStatus authenticate(String username, String password);

    /**
     * @param plaintext Plaintext password to check
     * @param hash Hash of the saved password
     * @return true of the plaintext password matches the hash value
     */
    boolean checkPassword(String plaintext, String hash);

    /**
     * @param plaintext Plaintext password to hash
     * @return A hashed value of the given plaintext password
     */
    String hashPassword(String plaintext);

    /**
     * @param userId Id of user to check
     * @param identifyable Object which has an id
     * @param permissionType The permission type to check
     * @return true if user is allowed to perform action or false if not
     */
    boolean isAuthorized(String userId, Identifyable identifyable, PermissionType permissionType);
}
