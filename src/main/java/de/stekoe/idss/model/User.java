package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;

@SuppressWarnings("serial")
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Systemrole> systemroles = new HashSet<Systemrole>(0);
    private UserProfile userProfile;
    private String activationKey;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Systemrole> getSystemroles() {
        return this.systemroles;
    }

    public void setSystemroles(Set<Systemrole> systemroles) {
        this.systemroles = systemroles;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setActivationKey(String key) {
        this.activationKey = key;
    }

    public String getActivationKey() {
        return this.activationKey;
    }

    @Override
    public String toString() {
        String format = "%-20s: %s %n";

        StringBuilder sb = new StringBuilder();

        sb.append("USER ==========================\n");
        sb.append(String.format(format, "ID", getId()));
        sb.append(String.format(format, "Username", getUsername()));
        sb.append(String.format(format, "Username", getEmail()));
        sb.append(String.format(format, "Password", getPassword()));

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
