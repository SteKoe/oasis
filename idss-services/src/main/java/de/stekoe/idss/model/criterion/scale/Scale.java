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

package de.stekoe.idss.model.criterion.scale;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Scale<T extends MeasurementValue> implements Serializable {

    private static final long serialVersionUID = 20141103926L;

    private ScaleId id = new ScaleId();
    private String name;
    private String description;
    private List<T> values = new ArrayList<T>();
    private SingleScaledCriterion criterion;

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "scale_id"))
    public ScaleId getId() {
        return id;
    }

    public void setId(ScaleId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OrderBy(value = "ordering")
    @OneToMany(targetEntity = MeasurementValue.class, cascade = CascadeType.ALL)
    public List<T> getValues() {
        return this.values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    public boolean isValid(MeasurementValue value) {
        return values.contains(value);
    }

    @OneToOne(targetEntity = SingleScaledCriterion.class)
    public SingleScaledCriterion getCriterion() {
        return criterion;
    }

    public void setCriterion(SingleScaledCriterion criterion) {
        this.criterion = criterion;
    }
}
