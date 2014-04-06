/*
 * Copyright 2014 Stephan Koeninger
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

package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectMember")
public class ProjectMember implements Serializable {

    private static final long serialVersionUID = 20141103926L;

    private String id = IDGenerator.createId();
    private User user;
    private ProjectRole projectRole;
    private Set<ProjectMemberGroup> projectGroups = new HashSet<ProjectMemberGroup>();

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public ProjectRole getProjectRole() {
        return this.projectRole;
    }

    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    @ManyToMany(targetEntity = ProjectMemberGroup.class)
    public Set<ProjectMemberGroup> getProjectGroups() {
        return this.projectGroups;
    }

    public void setProjectGroups(Set<ProjectMemberGroup> projectGroups) {
        this.projectGroups = projectGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (o.getClass() != getClass()) {
            return false;
        }
        ProjectMember that = (ProjectMember) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getId(), that.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .hashCode();
    }
}
