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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class PageElement implements NamedElement, Serializable {

    private static final long serialVersionUID = 20141103926L;

    private String id = IDGenerator.createId();
    private CriterionPage criterionPage;
    private int ordering;
    private String name;
    private String description;
    private boolean referenceType = false;
    private String originId;

    public PageElement() {
    }

    public PageElement(PageElement pageElement) {
        this.criterionPage = pageElement.getCriterionPage();
        this.name = pageElement.getName();
        this.description = pageElement.getDescription();
        this.originId = pageElement.getId();
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = CriterionPage.class)
    public CriterionPage getCriterionPage() {
        return criterionPage;
    }

    public void setCriterionPage(CriterionPage criterionPage) {
        this.criterionPage = criterionPage;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    @NotNull
    @Column(nullable = false)
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Lob
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isReferenceType() {
        return referenceType;
    }
    public void setReferenceType(boolean referenceType) {
        this.referenceType = referenceType;
    }

    public String getOriginId() {
        return originId;
    }
    public void setOriginId(String originId) {
        this.originId = originId;
    }
}
