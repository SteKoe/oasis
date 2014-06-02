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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import de.stekoe.idss.model.OrderableUtil.Direction;

@Entity
public class CriterionPage implements Serializable {
    private static final long serialVersionUID = 20141103925L;

    private String id = IDGenerator.createId();
    private String name;
    private List<PageElement> pageElements = new ArrayList<PageElement>();
    private Project project;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @OneToMany(mappedBy="criterionPage", targetEntity = PageElement.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    public List<PageElement> getPageElements() {
        if(pageElements == null) {
            return new ArrayList<PageElement>();
        }
        return pageElements;
    }
    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    @Transient
    public boolean move(PageElement pageElement, Direction direction) {
        List<PageElement> oldList = pageElements;
        List<PageElement> reorderedList = OrderableUtil.<PageElement>move(pageElements, pageElement, direction);
        setPageElements(reorderedList);
        return oldList.equals(reorderedList);
    }

    @ManyToOne(targetEntity = Project.class)
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof PageElement)) return false;

        CriterionPage that  = (CriterionPage) other;
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
