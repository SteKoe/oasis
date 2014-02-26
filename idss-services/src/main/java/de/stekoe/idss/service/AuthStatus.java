package de.stekoe.idss.service;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum AuthStatus {
    /**
     * Status when password was wrong
     */
    WRONG_PASSWORD,
    /**
     * Status when user hasn't been found
     */
    USER_NOT_FOUND,
    /**
     * Status when user hasn't been activated
     */
    USER_NOT_ACTIVATED,
    /**
     * Status when user has successfully been logged in
     */
    SUCCESS;
}
