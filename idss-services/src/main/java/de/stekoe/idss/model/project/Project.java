/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.model.project;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.GenericId;
import de.stekoe.idss.model.Identifyable;
import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.enums.ProjectStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class Project implements Serializable, Identifyable<ProjectId> {

    private static final long serialVersionUID = 20141103923L;

    private ProjectId id = new ProjectId();
    private String name;
    private String description;
    private Set<ProjectMember> projectTeam = new HashSet<ProjectMember>();
    private Set<Document> documents = new HashSet<Document>();
    private Set<ProjectRole> projectRoles = new HashSet<ProjectRole>();
    private List<Scale> scaleList = new ArrayList<Scale>();
    private ProjectStatus projectStatus = ProjectStatus.EDITING;
    private Date projectStartDate = new Date();
    private Date projectEndDate;

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "project_id"))
    public ProjectId getId() {
        return this.id;
    }

    @Override
    public void setId(ProjectId id) {
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
