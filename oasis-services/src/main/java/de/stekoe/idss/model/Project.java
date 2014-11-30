package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Project implements Serializable, Identifyable<String>, NamedElement {
    private static final long serialVersionUID = 20141103923L;

    private String id = IDGenerator.createId();
    private String name;
    private String description;
    private Set<ProjectMember> projectTeam = new HashSet<ProjectMember>();
    private Set<Document> documents = new HashSet<Document>();
    private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
    private EvaluationStatus projectStatus = EvaluationStatus.PREPARATION;
    private Date projectStartDate = new Date();
    private Date projectEndDate;

    @Override
    @Id
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @NotNull
    @Size(min = 2, max = 200)
    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    @Override
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

    @OneToMany(cascade = CascadeType.ALL, targetEntity = ProjectMember.class, fetch = FetchType.EAGER)
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

    @Enumerated(value = EnumType.STRING)
    public EvaluationStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(EvaluationStatus projectStatus) {
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

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Project)) return false;

        Project that  = (Project) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
