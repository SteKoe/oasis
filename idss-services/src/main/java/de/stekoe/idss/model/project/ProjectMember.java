package de.stekoe.idss.model.project;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.User;
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
    private ProjectRole projectRole;
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
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public ProjectRole getProjectRole() {
        return this.projectRole;
    }

    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    @ManyToMany(targetEntity = ProjectMemberGroup.class)
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
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .hashCode();
    }
}
