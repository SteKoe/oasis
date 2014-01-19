package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectMember")
public class ProjectMember implements Serializable {

    private java.lang.String id = IDGenerator.createId();
    private User user;
    private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
    private Set<ProjectMemberGroup> projectGroups = new HashSet<ProjectMemberGroup>();

    @Id
    @Column(name = "project_member_id")
    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(targetEntity = ProjectRole.class)
    @JoinTable(name = "ProjectMemberToProjectRole", joinColumns = @JoinColumn(name = "project_member_id"), inverseJoinColumns = @JoinColumn(name = "project_role_id"))
    public Set<ProjectRole> getProjectRoles() {
        return this.projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @ManyToMany(targetEntity = ProjectMemberGroup.class)
    @JoinTable(name = "ProjectMemberToProjectGroup", joinColumns = @JoinColumn(name = "project_member_id"), inverseJoinColumns = @JoinColumn(name = "project_group_id"))
    public Set<ProjectMemberGroup> getProjectGroups() {
        return this.projectGroups;
    }

    public void setProjectGroups(Set<ProjectMemberGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (o.getClass() != getClass()) { return false; }
        ProjectMember that = (ProjectMember) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getUser(), that.getUser())
                .append(getProjectRoles(), that.getProjectRoles())
                .append(getProjectGroups(), that.getProjectGroups())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUser())
                .append(getProjectRoles())
                .append(getProjectGroups())
                .hashCode();
    }
}
