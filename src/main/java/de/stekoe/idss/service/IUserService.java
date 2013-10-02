package de.stekoe.idss.service;

import java.util.List;

import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;

/**
 * @author Stephan Koeninger <mail@stekoe.de>
 */
public interface IUserService {

    /** Different stati for login. */
    public enum LoginStatus {
        /** Status when password was wrong */
        WRONG_PASSWORD,
        /** Status when user hasn't been found */
        USER_NOT_FOUND,
        /** Status when user hasn't been activated */
        USER_NOT_ACTIVATED,
        /** Status when user has successfully been logged in */
        SUCCESS
    }

    /**
     * Method to login a user by given username and password.
     *
     * @param username The username.
     * @param password The password as plaintext.
     * @return true on success, false otherwise.
     */
    LoginStatus login(String username, String password);

    /**
     * @param username Username to lookup.
     * @return a User object if entry found.
     */
    User findByUsername(String username);


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

    /**
     * Get a role form database by given rolename.
     *
     * @param rolename The rolename to look for
     * @return The role if found
     */
    Role getRole(String rolename);

    /**
     * @param user The user to save
     * @return True on success, false otherwise.
     * @throws EmailAddressAlreadyInUseException
     * @throws UsernameAlreadyInUseException
     */
    boolean save(User user) throws EmailAddressAlreadyInUseException, UsernameAlreadyInUseException;
}
