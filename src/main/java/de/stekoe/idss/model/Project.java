package de.stekoe.idss.model;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable {

    private String id;
    private String name;
    private String description;
    private Collection<ProjectMember> projectTeam = new HashSet<ProjectMember>(0);
    private Collection<File> files = new HashSet<File>(0);

    @Id
    @Column(name = "project_id")
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    public Collection<ProjectMember> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Collection<ProjectMember> projectTeam) {
        this.projectTeam = projectTeam;
    }

    @ManyToMany
    @JoinTable(name = "ProjectFiles", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    public Collection<File> getFiles() {
        return this.files;
    }

    public void setFiles(Collection<File> files) {
        this.files = files;
    }

    @Transient
    public boolean userIsMember(final User user) {
        for(ProjectMember pm : getProjectTeam()) {
            if(pm.getUser().equals(user)) {
                return true;
            }
        }

        return true;
    }

    @Transient
    public Collection<ProjectRole> getProjectRolesForUser(final User user) {

        ProjectMember projectMemberObject = (ProjectMember)CollectionUtils.find(this.getProjectTeam(), new Predicate() {
            public boolean evaluate(Object o) {
                ProjectMember c = (ProjectMember) o;
                final String userIdToCheck = user.getId();
                final String currentUserId = c.getUser().getId();
                return currentUserId.equals(userIdToCheck);
            }
        });

        if(projectMemberObject == null) {
            return Collections.emptySet();
        }

        return projectMemberObject.getProjectRoles();
    }

    @Transient
    public boolean userHasRole(ProjectRole role, User user) {
        Collection<ProjectRole> userRoles = getProjectRolesForUser(user);

        return userRoles.contains(role);
    }
}
