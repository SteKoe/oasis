package de.stekoe.idss.model.enums;

/**
 * In order to add more semantic to a users status, this enumeration may be used to
 * set a user's status.
 *
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum UserStatus {
    /**
     * Just for test users. Users having this status will not be able to
     * login in production mode!
     */
    TEST,
    /**
     * All registered users will have this status until they have activated their account.
     */
    ACTIVATION_PENDING,
    /**
     * Registered users who have activated their account.
     */
    ACTIVATED,
    /**
     * Admins are able to deactivate user accounts.
     */
    DEACTIVATED,
    /**
     * If a user wants the admin not just to disable the account, this status marks the user as
     * deleted.
     */
    DELETED,
    /**
     *
     */
    UNKNOWN
}
