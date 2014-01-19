package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class ProjectMemberGroup {
    private String id = IDGenerator.createId();
    private String name;
    private Project project;

    @Id
    @Column(name = "project_member_group_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @ManyToOne(targetEntity = Project.class)
    @JoinTable(name = "ProjectToProjectMemberGroup", joinColumns = @JoinColumn(name = "project_member_group_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
