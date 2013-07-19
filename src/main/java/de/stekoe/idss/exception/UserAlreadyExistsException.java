package de.stekoe.idss.exception;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class UserAlreadyExistsException extends Exception {

    /**
     * Construct.
     */
    public UserAlreadyExistsException() {
        super(
                "User with the given username already exists in database. Usernames must be unique!");
    }

}
