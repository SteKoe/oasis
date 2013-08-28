package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface UserDAO {
    /**
     * @param username The username to look for.
     * @return The user with the given username.
     */
    User findByUsername(String username);

    /**
     * @return A List of all users.
     */
    List<User> getAllUsers();

    /**
     * @param user A User to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean save(User user);

    /**
     * @param code The activationcode to look for.
     * @return The user with the given username.
     */
    User findByActivationCode(String code);

    /**
     * @param email The email address to look for.
     * @return The user with the given email address.
     */
    User findByEmail(String email);

    /**
     * @param id The id of the user to look for.
     * @return The user with given id.
     */
    User findById(String id);
}
