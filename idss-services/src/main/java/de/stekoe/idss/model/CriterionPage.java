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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

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

    @PreRemove
    private void preRemove() {
        for(PageElement pe : pageElements) {
            pe.setCriterionPage(null);
        }
    }

    /**
     * If you want to add new elements use {@code CriterionPage#addPageElement(PageElement)}.
     *
     * @return A list of all PageElements of this CriterionPage
     */
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = PageElement.class, cascade = CascadeType.ALL)
    @OrderColumn(name = "ordering")
    public List<PageElement> getPageElements() {
        return pageElements;
    }
    public void setPageElements(List<PageElement> pageElements) {
        this.pageElements = pageElements;
    }
    public void addPageElement(PageElement pageElement) {
        if(!pageElements.contains(pageElement)) {
            pageElements.add(pageElement);
            pageElement.setCriterionPage(this);
        }
    }

    @Transient
    public boolean move(PageElement pageElement, Direction direction) {
        return OrderableUtil.<PageElement>move(pageElements, pageElement, direction);
    }

    @ManyToOne(targetEntity = Project.class, optional = false)
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
}
