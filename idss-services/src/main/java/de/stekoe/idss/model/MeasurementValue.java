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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {
    private static final long serialVersionUID = 201404132234L;

    private String id = IDGenerator.createId();
    private String value;
    private SingleScaledCriterion criterion;
    private int ordering;

    protected MeasurementValue() {
        // NOP
    }

    protected MeasurementValue(String value) {
        this.ordering = 0;
        this.value = value;
    }

    protected MeasurementValue(int ordering, String value) {
        this.ordering = ordering;
        this.value = value;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCriterion(SingleScaledCriterion criterion) {
        this.criterion = criterion;
    }

    @ManyToOne(targetEntity = SingleScaledCriterion.class)
    public SingleScaledCriterion getCriterion() {
        return criterion;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    public int hashCode() {
        if(id == null) {
            return 0;
        } else {
            return id.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        MeasurementValue rhs = (MeasurementValue) obj;

        return new EqualsBuilder()
                .append(getId(), rhs.id)
                .isEquals();
    }

}
