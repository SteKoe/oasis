package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectRole")
public class ProjectRole implements Serializable {

    public static final String LEADER_CONSTANT = "LEADER";
    public static final String MEMBER_CONSTANT = "MEMBER";

    public static final ProjectRole LEADER = new ProjectRole(LEADER_CONSTANT);
    public static final ProjectRole MEMBER = new ProjectRole(MEMBER_CONSTANT);

    private String id;
    private String name;
    private Collection<ProjectMember> projectMembers = new HashSet<ProjectMember>(0);

    public ProjectRole() {

    }

    private ProjectRole(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "project_role_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Length(min = 5)
    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "projectRoles")
    public Collection<ProjectMember> getProjectMembers() {
        return this.projectMembers;
    }

    public void setProjectMembers(Collection<ProjectMember> projectMembers) {
        this.projectMembers = projectMembers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectRole that = (ProjectRole) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
