package de.stekoe.idss.model;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
public class User implements Serializable {

    private static final int MIN_PASSWORD_LENGTH = 5;

    private String id;
    private Collection<SystemRole> roles = new HashSet<SystemRole>(0);
    private UserProfile userProfile;
    private String username;
    private String password;
    private String email;
    private String activationKey;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public UserProfile getProfile() {
        return this.userProfile;
    }

    public void setProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @NotNull
    @Size(min = 1)
    @Column(unique = true, nullable = false)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(min = MIN_PASSWORD_LENGTH)
    @Column(unique = true, nullable = false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Size(min = 1)
    @Column(unique = true, nullable = false)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    public String getActivationKey() {
        return this.activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity = SystemRole.class)
    @JoinTable(name="UserToSystemRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<SystemRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Collection<SystemRole> roles) {
        this.roles = roles;
    }

    @Transient
    public boolean isAdmin() {
        for (SystemRole role : getRoles()) {
            if(role.getName().equals(Roles.ADMIN))
                return true;
        }

        return false;
    }

    public boolean hasAnyRole(Roles rolesToCheck) {
        for (String roleToCheck : rolesToCheck) {
            for (SystemRole currentRole : getRoles()) {
                if(currentRole.getName().equals(roleToCheck))
                    return true;
            }
        }

        return false;
    }

}
