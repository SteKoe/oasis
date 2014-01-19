package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.enums.ProjectStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable, Identifyable {

    private java.lang.String id = IDGenerator.createId();
    private java.lang.String name;
    private java.lang.String description;
    private Set<ProjectMember> projectTeam = new HashSet<ProjectMember>(0);
    private Set<File> files = new HashSet<File>(0);
    private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
    private ProjectStatus projectStatus = ProjectStatus.EDITING;

    @Id
    @Column(name = "project_id")
    public java.lang.String getId() {
        return this.id;
    }

    public void setId(java.lang.String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable=false)
    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    @Lob
    @NotNull
    public java.lang.String getDescription() {
        return this.description;
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProjectMember.class)
    public Set<ProjectMember> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Set<ProjectMember> projectTeam) {
        this.projectTeam = projectTeam;
    }

    @ManyToMany(targetEntity = File.class)
    @JoinTable(name = "ProjectFiles", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "file_id"))
    public Set<File> getFiles() {
        return this.files;
    }

    public void setFiles(Set<File> files) {
        this.files = files;
    }

    @OneToMany(targetEntity = ProjectRole.class, cascade = CascadeType.ALL)
    public Set<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @Enumerated(value = EnumType.STRING)
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }
}
