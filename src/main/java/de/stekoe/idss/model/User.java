package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

/**
 * This class represents the concept of a User.
 *
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String email;
    @ElementCollection
    private Set<String> systemroles = new HashSet<String>();
    private UserProfile userProfile;
    private String activationKey;

    /**
     * @return id of User.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id The id for the user generated by hibernate.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     *
     * @param username The username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the encrypted password of a user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     *
     * @param password The encrypted password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return a set of systemroles
     */
    public Set<String> getSystemroles() {
        return this.systemroles;
    }

    /**
     * Set systemroles.
     *
     * @param systemroles A {@code Set} of {@link Systemrole}s.
     */
    public void setSystemroles(Set<String> systemroles) {
        this.systemroles = systemroles;
    }

    /**
     * @return the user's profile.
     */
    public UserProfile getUserProfile() {
        return userProfile;
    }

    /**
     * Set the userprofile.
     *
     * @param userProfile The {@link UserProfile} of a user.
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Set email.
     *
     * @param email The email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return user's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set the activation key for a user.
     * <p>
     * The activation key is necessary to activate a user account. If a user has
     * registered, its account is deactivated until he activates it via its
     * activation key.
     *
     * @param key A unique activation key for the user account.
     */
    public void setActivationKey(String key) {
        this.activationKey = key;
    }

    /**
     * @return the user's activation key.
     */
    public String getActivationKey() {
        return this.activationKey;
    }

    @Override
    public String toString() {

        if(1 == 1) return "";

        String format = "%-20s: %s %n";

        StringBuilder sb = new StringBuilder();

        sb.append("USER ==========================\n");
        sb.append(String.format(format, "ID", getId()));
        sb.append(String.format(format, "Username", getUsername()));
        sb.append(String.format(format, "Username", getEmail()));
        sb.append(String.format(format, "Roles", getSystemroles().toString()));

        return sb.toString();
    }

    /**
     * Checks wether the user has at least one of the roles which are passed
     * into this method.
     *
     * @param roles Roles which are checked wether the user has one of them.
     * @return true if the user has any of the given roles. False otherwise.
     */
    public boolean hasAnyRole(Roles roles) {

        Roles systemroles = new Roles();
        for (String systemrole : getSystemroles()) {
            systemroles.add(systemrole);
        }

        System.out.println("User needs role: " + roles);
        System.out.println("User has roles:  " + systemroles);

        return systemroles.hasAnyRole(roles);
    }
}
