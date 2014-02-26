package de.stekoe.idss.service;

import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;

import java.util.List;

public interface UserService {
    /**
     * Find a specific user by its username
     * @param username The username to look for
     * @return The user if found or null
     */
    User findByUsername(String username);

    /**
     * Finds a list of users which usernames match the given username.
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

    User findById(String id);

    void delete(User entity);
}
