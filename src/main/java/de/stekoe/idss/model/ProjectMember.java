package de.stekoe.idss.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectMember")
public class ProjectMember implements Serializable {

    private String id;
    private User user;
    private Collection<ProjectRole> projectRoles = new HashSet<ProjectRole>(0);
    private Collection<ProjectMemberGroup> projectGroups = new HashSet<ProjectMemberGroup>(0);
    private Project project;

    @Id
    @Column(name = "project_member_id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectRole.class)
    @JoinTable(name = "ProjectMemberToProjectRole", joinColumns = @JoinColumn(name = "project_member_id"), inverseJoinColumns = @JoinColumn(name = "project_role_id"))
    public Collection<ProjectRole> getProjectRoles() {
        return this.projectRoles;
    }

    public void setProjectRoles(Collection<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectMemberGroup.class)
    @JoinTable(name = "ProjectMemberToProjectGroup", joinColumns = @JoinColumn(name = "project_member_id"), inverseJoinColumns = @JoinColumn(name = "project_group_id"))
    public Collection<ProjectMemberGroup> getProjectGroups() {
        return this.projectGroups;
    }

    public void setProjectGroups(Collection<ProjectMemberGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    // UTILS ==================================================

    @Transient
    public boolean isLeader() {
        return this.projectRoles.contains(ProjectRole.LEADER);
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
                .append(getProject(), that.getProject())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getUser())
                .append(getProjectRoles())
                .append(getProjectGroups())
                .append(getProject())
                .hashCode();
    }
}
