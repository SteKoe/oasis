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

import javax.persistence.Entity;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class OrdinalValue extends MeasurementValue {
    private static final long serialVersionUID = 201404132235L;

    private int rank;

    public OrdinalValue() {
        // NOP
    }

    /**
     * @param rank  Rank of the current value
     * @param value The value itself
     */
    public OrdinalValue(int rank, String value) {
        super(value);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isGreaterThan(OrdinalValue aVal) {
        return getRank() > aVal.getRank();
    }

    public boolean isLowerThan(OrdinalValue aVal) {
        return this.getRank() < aVal.getRank();
    }
}
