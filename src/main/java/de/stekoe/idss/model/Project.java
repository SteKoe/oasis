package de.stekoe.idss.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable {

    private String id;
    private String name;
    private String description;
    private Set<ProjectMember> projectTeam = new HashSet<ProjectMember>(0);

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable=false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ProjectMember.class, cascade = CascadeType.ALL)
    @JoinTable(name = "ProjectToProjectMember")
    public Set<ProjectMember> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Set<ProjectMember> projectTeam) {
        this.projectTeam = projectTeam;
    }

    @Transient
    public Set<ProjectRole> getProjectRolesForUser(final User user) {

        ProjectMember projectMemberObject = (ProjectMember)CollectionUtils.find(this.getProjectTeam(), new Predicate() {
            public boolean evaluate(Object o) {
                ProjectMember c = (ProjectMember) o;
                return c.getUser().equals(user);
            }
        });

        if(projectMemberObject == null) {
            return Collections.emptySet();
        }

        return projectMemberObject.getProjectRoles();
    }
}
