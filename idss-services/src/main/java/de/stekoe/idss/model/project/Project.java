package de.stekoe.idss.model.project;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.enums.ProjectStatus;
import de.stekoe.idss.model.scale.Scale;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Project")
public class Project implements Serializable, Identifyable {

    private String id = IDGenerator.createId();
    private String name;
    private String description;
    private Set<ProjectMember> projectTeam = new HashSet<ProjectMember>();
    private Set<Document> documents = new HashSet<Document>();
    private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
    private List<Scale> scaleList = new ArrayList<Scale>();
    private ProjectStatus projectStatus = ProjectStatus.EDITING;
    private Date projectStartDate = new Date();
    private Date projectEndDate;

    @Id
    @Column(name = "project_id")
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
    @NotNull
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProjectMember.class)
    public Set<ProjectMember> getProjectTeam() {
        return projectTeam;
    }

    public void setProjectTeam(Set<ProjectMember> projectTeam) {
        this.projectTeam = projectTeam;
    }

    @ManyToMany(targetEntity = Document.class)
    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        this.documents = documents;
    }

    @OneToMany(targetEntity = ProjectRole.class, cascade = CascadeType.ALL)
    public Set<ProjectRole> getProjectRoles() {
        return projectRoles;
    }

    public void setProjectRoles(Set<ProjectRole> projectRoles) {
        this.projectRoles = projectRoles;
    }

    @OneToMany(targetEntity = Scale.class)
    public List<Scale> getScaleList() {
        return scaleList;
    }

    public void setScaleList(List<Scale> scaleList) {
        this.scaleList = scaleList;
    }

    @Enumerated(value = EnumType.STRING)
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    @Temporal(TemporalType.DATE)
    @DateTimeFormat
    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }
}
