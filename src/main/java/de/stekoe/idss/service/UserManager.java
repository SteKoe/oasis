package de.stekoe.idss.service;

import java.util.List;

import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface UserManager {

    /**
     * @param user The User object which is to be persisted.
     * @return true on success, false otherwise.
     * @throws UserAlreadyExistsException
     */
    boolean insertUser(User user) throws UserAlreadyExistsException;

    /**
     * Method to login a user by given username and password.
     *
     * @param username The username.
     * @param password The password.
     * @return true on success, false otherwise.
     */
    boolean login(String username, String password);

    /**
     * @param username Username to lookup.
     * @return a User object if entry found.
     */
    User findByUsername(String username);

    /**
     * @param user User to update.
     */
    void update(User user);

    /**
     * @return List of all users in database.
     */
    List<User> getAllUsers();

    /**
     * @param code Activation code to look up.
     * @return the User with the given activation code
     */
    User findByActivationCode(String code);

    /**
     * @return List holding all usernames of database.
     */
    List<String> getAllUsernames();

    /**
     * @return List of all email addresses in database.
     */
    List<String> getAllEmailAddresses();
}
