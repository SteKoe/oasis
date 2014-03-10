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

import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserId;

import java.util.List;

public interface UserService {
    /**
     * @param username The username to look for
     * @return The user if found or null
     */
    User findByUsername(String username);

    /**
     * @param username The username (or part of it) to lookup
     * @return A user who's username contains the given username
     */
    List<User> findAllByUsername(String username);

    /**
     * @param email The email to lookup
     * @return The user with the given email
     */
    User findByEmail(String email);

    /**
     * @param usernameOrEmail The username or email address to lookup
     * @return The user with the given username or email
     */
    User findByUsernameOrEmail(String usernameOrEmail);

    /**
     * @return A list of all users
     */
    List<User> getAllUsers();

    /**
     * @param code The activation code to lookup
     * @return The user with the given activation code
     */
    User findByActivationCode(String code);

    /**
     * @return A list of all usernames
     */
    List<String> getAllUsernames();

    /**
     * @return A list of all email addresses
     */
    List<String> getAllEmailAddresses();

    void save(User entity) throws UserException;

    /**
     * @param id The id of the user to retrieve
     * @return The user if found or null
     */
    User findById(UserId id);

    /**
     * @param user The user to delete
     */
    void delete(User user);
}
