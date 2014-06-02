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
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {
    private static final long serialVersionUID = 201404132234L;

    private String id = IDGenerator.createId();
    private String value;
    private SingleScaledCriterion criterion;

    protected MeasurementValue() {
        // NOP
    }

    protected MeasurementValue(String value) {
        this.value = value;
    }

    public MeasurementValue(MeasurementValue measurementValue) {
        value = measurementValue.getValue();
        criterion = measurementValue.getCriterion();
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

    @ManyToOne(targetEntity = SingleScaledCriterion.class)
    @OrderColumn(name = "ordering")
    public SingleScaledCriterion getCriterion() {
        return criterion;
    }
    public void setCriterion(SingleScaledCriterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof MeasurementValue)) return false;

        MeasurementValue that  = (MeasurementValue) other;
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
