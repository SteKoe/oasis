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

package de.stekoe.idss.service;

import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.UserId;
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
    boolean isAuthorized(UserId userId, Identifyable identifyable, PermissionType permissionType);
}
