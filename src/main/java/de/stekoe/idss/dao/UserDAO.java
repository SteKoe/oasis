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
     * @param entity Update the given User.
     */
    void update(User entity);

    /**
     * @return A List of all users.
     */
    List<User> getAllUsers();

    /**
     * @param user A User to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean insert(User user);

    /**
     * @param code The activationcode to look for.
     * @return The user with the given username.
     */
    User findByActivationCode(String code);
}
