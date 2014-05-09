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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

@Entity
public abstract class SingleScaledCriterion<T extends MeasurementValue> extends Criterion {

    private static final long serialVersionUID = 201403181647L;

    private List<T> values = new ArrayList<T>();

    @OrderBy(value = "ordering")
    @OneToMany(targetEntity = MeasurementValue.class, cascade = CascadeType.ALL)
    public List<T> getValues() {
        return this.values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }

    @Transient
    public void addValue(T value) {
        int ordering = getNextValueOrdering();
        value.setOrdering(ordering);
        getValues().add(value);
    }

    @Transient
    int getNextValueOrdering() {
        if(getValues().size() == 0) {
            return 0;
        }

        T max = Collections.max(getValues(), new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return Integer.valueOf(o1.getOrdering()).compareTo(Integer.valueOf(o2.getOrdering()));
            }
        });
        return max.getOrdering() + 1;
    }

    @Transient
    public void moveUp(T value) {
        int index = getValues().indexOf(value);
        value = getValues().get(index);

        if(value != null && value.getOrdering() > 0) {
            T upperValue = getValueWithOrdering(value.getOrdering() - 1);
            upperValue.setOrdering(value.getOrdering());
            value.setOrdering(value.getOrdering() - 1);
        }
    }

    @Transient
    public void moveDown(T value) {
        int index = getValues().indexOf(value);
        value = getValues().get(index);

        if(value != null && value.getOrdering() < getValues().size() - 1) {
            T upperValue = getValueWithOrdering(value.getOrdering() + 1);
            upperValue.setOrdering(value.getOrdering());
            value.setOrdering(value.getOrdering() + 1);
        }
    }

    @Transient
    T getValueWithOrdering(final int ordering) {
        for(T value : getValues()) {
            if(value.getOrdering() == ordering) {
                return value;
            }
        }

        return null;
    }
}
