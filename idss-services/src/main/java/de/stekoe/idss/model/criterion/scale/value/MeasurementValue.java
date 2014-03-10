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

package de.stekoe.idss.model.criterion.scale.value;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.criterion.scale.Scale;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {

    private MeasurementValueId id = new MeasurementValueId();
    private String value;
    private Scale scale;
    private int ordering;

    protected MeasurementValue() {
        // NOP
    }

    protected MeasurementValue(int ordering, String value) {
        this.ordering = ordering;
        this.value = value;
    }

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "masurement_value_id"))
    public MeasurementValueId getId() {
        return id;
    }

    public void setId(MeasurementValueId id) {
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

    public void setScale(Scale scale) {
        this.scale = scale;
    }

    @ManyToOne(targetEntity = Scale.class)
    public Scale getScale() {
        return scale;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    public int hashCode() {
        if(value == null) {
            return 0;
        } else {
            return value.hashCode();
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
                .append(getValue(), rhs.value)
                .isEquals();
    }

}
