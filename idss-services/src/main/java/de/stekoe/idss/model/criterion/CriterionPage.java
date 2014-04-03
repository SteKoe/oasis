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

package de.stekoe.idss.model.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import de.stekoe.idss.model.project.Project;

@Entity
public class CriterionPage implements Serializable {

    private static final long serialVersionUID = 20141103925L;

    private CriterionPageId id = new CriterionPageId();
    private int ordering = -1;
    private String name;
    private List<PageElement> pageElements = new ArrayList<PageElement>();
    private Project project;

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "criterion_page_id"))
    public CriterionPageId getId() {
        return id;
    }

    public void setId(CriterionPageId id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @OrderBy(value = "ordering")
    @OneToMany(targetEntity = PageElement.class, cascade = CascadeType.ALL)
    public List<PageElement> getPageElements() {
        return pageElements;
    }

    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }

    @ManyToOne(targetEntity = Project.class, optional = false)
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getName() {
        if (name == null) {
            return String.valueOf(getOrdering());
        }

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
