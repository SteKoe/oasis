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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.ToStringBuilder;

import de.stekoe.idss.model.OrderableUtil.Direction;

@Entity
public abstract class SingleScaledCriterion<T extends MeasurementValue> extends Criterion {

    private static final long serialVersionUID = 201403181647L;

    private List values = new ArrayList();

    public SingleScaledCriterion() {
    }

    public SingleScaledCriterion(SingleScaledCriterion<T> singleScaledCriterion) {
        super(singleScaledCriterion);

        for(Object mv : singleScaledCriterion.getValues()) {
            if(mv instanceof OrdinalValue) {
                values.add(new OrdinalValue((OrdinalValue) mv));
            } else if(mv instanceof NominalValue) {
                values.add(new NominalValue((NominalValue) mv));
            }
        }
    }

    @OneToMany(fetch = FetchType.EAGER, targetEntity = MeasurementValue.class, cascade = CascadeType.ALL)
    @OrderColumn(name = "ordering")
    public List<T> getValues() {
        return this.values;
    }
    public void setValues(List<T> values) {
        this.values = values;
    }

    @Transient
    public boolean move(T value, Direction direction) {
        return OrderableUtil.<T>move(values, value, direction);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append(super.toString())
            .toString();
    }
}
