package de.stekoe.oasis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Project implements Serializable, Identifyable<String>, NamedElement {
    private static final long serialVersionUID = 20141103923L;

    private String id;
    private String name;
    private String description;
    private Set<ProjectMember> projectTeam = new HashSet<>();
    private Set<Document> documents = new HashSet<>();
    private List<ProjectRole> projectRoles = new LinkedList<>();
    private EvaluationStatus projectStatus = EvaluationStatus.PREPARATION;
    private Date projectStartDate = new Date();
    private Date projectEndDate;

    public Project() {
    }

    public Project(ProjectDescriptor projectDescriptor) {
        this.id = projectDescriptor.getId();
        this.name = projectDescriptor.getName();
        this.description = projectDescriptor.getDescription();
    }

    @Override
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @OrderColumn(name = "ordering")
    public List<ProjectRole> getProjectRoles() {
        return projectRoles;
    }
    public void setProjectRoles(List<ProjectRole> projectRoles) {
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
        if (this == other) return true;
        if (!(other instanceof Project)) return false;

        Project that = (Project) other;
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

    @Transient
    public List<ProjectMember> getMembersByRole(ProjectRole projectRole) {
        return getProjectTeam().stream().filter(projectMember -> projectMember.getProjectRole().equals(projectRole)).collect(Collectors.toList());
    }
}
