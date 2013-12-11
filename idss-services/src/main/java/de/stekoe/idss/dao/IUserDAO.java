package de.stekoe.idss.dao;

import de.stekoe.idss.model.User;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IUserDAO extends IGenericDAO<User> {

    /**
     * @param username The username to look for.
     * @return The user with the given username.
     */
    User findByUsername(String username);

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
}
