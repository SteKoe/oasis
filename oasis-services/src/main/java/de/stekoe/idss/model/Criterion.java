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
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
public abstract class Criterion extends PageElement implements Serializable {

    private static final long serialVersionUID = 20141103925L;

    private boolean allowNoChoice = false;
    private Set<CriterionGroup> criterionGroups = new HashSet<CriterionGroup>();

    public Criterion() {
        // NOP
    }

    public Criterion(Criterion criterion) {
        super(criterion);
        this.allowNoChoice = criterion.isAllowNoChoice();
    }

    @PreRemove
    private void removeFromCriterionGroups() {
        Iterator<CriterionGroup> cgIterator = getCriterionGroups().iterator();
        while(cgIterator.hasNext()) {
            Iterator<Criterion> criterionIterator = cgIterator.next().getCriterions().iterator();
            while(criterionIterator.hasNext()) {
                if(this.equals(criterionIterator.next())) {
                    criterionIterator.remove();
                }
            }
        }
    }

    @Column(columnDefinition = "boolean default false")
    public boolean isAllowNoChoice() {
        return allowNoChoice;
    }
    public void setAllowNoChoice(boolean aAllowNoChoice) {
        allowNoChoice = aAllowNoChoice;
    }

    @ManyToMany(targetEntity = CriterionGroup.class, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    public Set<CriterionGroup> getCriterionGroups() {
        return criterionGroups;
    }
    public void setCriterionGroups(Set<CriterionGroup> criterionGroups) {
        this.criterionGroups = criterionGroups;
    }

    @Transient
    public boolean isMemberOfGroup() {
        return this.getCriterionGroups().size() != 0;
    }
    /**
     * Copies the given criterion.
     *
     * @param criterion The criterion to copy
     * @return The copied criterion or null on failure
     */
    public static Criterion copyCriterion(Criterion criterion) {
        if(criterion instanceof MultiScaledCriterion) {
            return new MultiScaledCriterion((MultiScaledCriterion) criterion);
        } else if(criterion instanceof NominalScaledCriterion) {
            return new NominalScaledCriterion((NominalScaledCriterion) criterion);
        } else if(criterion instanceof OrdinalScaledCriterion) {
            return new OrdinalScaledCriterion((OrdinalScaledCriterion) criterion);
        }

        return null;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Criterion)) return false;

        Criterion that  = (Criterion) other;
        return new EqualsBuilder()
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
