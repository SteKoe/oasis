package de.stekoe.idss.model;

import de.stekoe.idss.model.enums.UserStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.hibernate.annotations.GenericGenerator;
import org.mindrot.jbcrypt.BCrypt;

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
    private UserStatus userStatus = UserStatus.UNKNOWN;
    private Collection<ProjectMember> projectMemberships = new HashSet<ProjectMember>(0);

    /**
     * Initialises a standard user object with activation key and user status UserStatus.ACTIVATION_PENDING
     */
    public User() {
        setUserStatus(UserStatus.ACTIVATION_PENDING);
        setActivationKey(DigestUtils.md5Hex(BCrypt.gensalt()));
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity = UserProfile.class, mappedBy = "user")
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
    @Column(nullable = false)
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

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity = SystemRole.class)
    @JoinTable(name="UserToSystemRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Collection<SystemRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Collection<SystemRole> roles) {
        this.roles = roles;
    }

    @Enumerated(EnumType.STRING)
    public UserStatus getUserStatus() {
        return this.userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        if(UserStatus.ACTIVATED.equals(userStatus)) {
            setActivationKey(null);
        }
        this.userStatus = userStatus;
    }

    @OneToMany(mappedBy = "user")
    public Collection<ProjectMember> getProjectMemberships() {
        return this.projectMemberships;
    }

    public void setProjectMemberships(Collection<ProjectMember> projectMemberships) {
        this.projectMemberships = projectMemberships;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!id.equals(user.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
