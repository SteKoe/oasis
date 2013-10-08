package de.stekoe.idss.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
    private Set<ProjectMember> projectTeam;

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

    public void setName(String projectName) {
        this.name = projectName;
    }

    @Lob
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = ProjectMember.class)
    @JoinTable(name = "ProjectToProjectMember")
    public Set<ProjectMember> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Set<ProjectMember> projectTeam) {
        this.projectTeam = projectTeam;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectTeam=" + projectTeam +
                '}';
    }
}
