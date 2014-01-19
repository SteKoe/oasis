package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.enums.UserStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
public class User implements Serializable {

    private static final int MIN_PASSWORD_LENGTH = 5;

    private String id = IDGenerator.createId();
    private Set<SystemRole> roles = new HashSet<SystemRole>(0);
    private UserProfile userProfile;
    private String username;
    private String password;
    private String email;
    private String activationKey;
    private UserStatus userStatus = UserStatus.UNKNOWN;
    private Set<ProjectMember> projectMemberships = new HashSet<ProjectMember>(0);
    private Set<Permission> permissions = new HashSet<Permission>();

    /**
     * Initialises a standard user object with activation key and user status UserStatus.ACTIVATION_PENDING
     */
    public User() {
        setUserStatus(UserStatus.ACTIVATION_PENDING);
        setActivationKey(DigestUtils.md5Hex(UUID.randomUUID().toString()));
    }

    @Id
    @Column(name = "user_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY, targetEntity = UserProfile.class)
    @PrimaryKeyJoinColumn
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

    @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, targetEntity = SystemRole.class)
    @JoinTable(name="UserToSystemRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "system_role_id"))
    public Set<SystemRole> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<SystemRole> roles) {
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

    @OneToMany(mappedBy = "user", targetEntity = ProjectMember.class)
    public Set<ProjectMember> getProjectMemberships() {
        return this.projectMemberships;
    }

    public void setProjectMemberships(Set<ProjectMember> projectMemberships) {
        this.projectMemberships = projectMemberships;
    }

    @OneToMany(targetEntity=Permission.class, cascade = CascadeType.ALL)
    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Transient
    public boolean isAdmin() {
        for (SystemRole role : getRoles()) {
            if(role.getName().equals(SystemRole.ADMIN))
                return true;
        }

        return false;
    }

    public boolean hasAnyRole(List<SystemRole> rolesToCheck) {
        for (SystemRole roleToCheck : rolesToCheck) {
            for (SystemRole currentRole : getRoles()) {
                if(currentRole.getName().equals(roleToCheck))
                    return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) { return false; }
        User rhs = (User) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(getId(), rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .hashCode();
    }
}