package de.stekoe.idss.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "User", uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
public class User implements Serializable {
    private static final long serialVersionUID = 201404031316L;

    private static final int MIN_PASSWORD_LENGTH = 5;

    private String id;
    private Set<SystemRole> roles = new HashSet<SystemRole>(0);
    private UserProfile userProfile;
    private String username;
    private String password;
    private String email;
    private String activationKey = DigestUtils.md5Hex(UUID.randomUUID().toString());
    private UserStatus userStatus = UserStatus.ACTIVATION_PENDING;
    private Set<ProjectMember> projectMemberships = new HashSet<ProjectMember>();
    private Set<Permission> permissions = new HashSet<Permission>();

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserProfile.class)
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

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = SystemRole.class, cascade = {CascadeType.PERSIST})
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "system_role_id")})
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
        if (UserStatus.ACTIVATED.equals(userStatus)) {
            setActivationKey(null);
        }
        this.userStatus = userStatus;
    }

    @OneToMany(targetEntity = ProjectMember.class)
    public Set<ProjectMember> getProjectMemberships() {
        return this.projectMemberships;
    }
    public void setProjectMemberships(Set<ProjectMember> projectMemberships) {
        this.projectMemberships = projectMemberships;
    }

    @OneToMany(targetEntity = Permission.class, cascade = CascadeType.ALL)
    public Set<Permission> getPermissions() {
        return permissions;
    }
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return true if the user has admin role, false otherwise
     */
    @Transient
    public boolean isAdmin() {
        for (SystemRole role : getRoles()) {
            if (SystemRole.ADMIN.equals(role.getName())) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param rolesToCheck List of roles which are checked the user has
     * @return true if the user has all given roles, false otherwise
     */
    @Transient
    public boolean hasAnyRole(List<SystemRole> rolesToCheck) {
        if (rolesToCheck != null) {
            for (SystemRole roleToCheck : rolesToCheck) {
                for (SystemRole currentRole : getRoles()) {
                    if (currentRole.getName().equals(roleToCheck)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof User)) return false;

        User that = (User) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
