package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Systemrole> systemroles = new HashSet<Systemrole>(0);
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
     * @param id
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
     * @param username
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
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return a set of systemroles 
     */
    public Set<Systemrole> getSystemroles() {
        return this.systemroles;
    }

    /**
     * Set systemroles.
     * 
     * @param systemrole
     */
    public void setSystemroles(Set<Systemrole> systemroles) {
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
     * @param userProfile
     */
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    /**
     * Set email.
     * 
     * @param email
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
     * <p>The activation key is necessary to activate a user account. 
     * If a user has registered, its account is deactivated until he activates it via its activation key.
     * 
     * @param key
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

    /**
     * Returns a string representation of the user.
     * <p>In order to prevent password leaks, the password will not be shown!
     */
    @Override
    public String toString() {
        String format = "%-20s: %s %n";

        StringBuilder sb = new StringBuilder();

        sb.append("USER ==========================\n");
        sb.append(String.format(format, "ID", getId()));
        sb.append(String.format(format, "Username", getUsername()));
        sb.append(String.format(format, "Username", getEmail()));

        Systemrole[] systemroles = getSystemroles().toArray(new Systemrole[0]);

        StringBuilder roles = new StringBuilder();
        if (systemroles.length > 0) {
            roles.append(systemroles[0].getName());

            for (int i = 1; i < systemroles.length; i++) {
                roles.append(",");
                roles.append(systemroles[i].getName());
            }
        }

        sb.append(String.format(format, "Roles", roles.toString()));

        return sb.toString();
    }

    /**
     * Checks wether the user has at least one of the roles which are passed into this method.
     * 
     * @param roles which are checked wether the user has.
     * @return true if the user has any of the given roles. False otherwise.
     */
    public boolean hasAnyRole(Roles roles) {

        Roles systemroles = new Roles();
        for (Systemrole systemrole : getSystemroles()) {
            systemroles.add(systemrole.getName());
        }

        System.out.println("User needs role: " + roles);
        System.out.println("User has roles:  " + systemroles);

        return systemroles.hasAnyRole(roles);
    }
}
