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

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class MultiScaledCriterion extends Criterion {
    private static final long serialVersionUID = 201404132234L;

    private List<SingleScaledCriterion> subCriterions = new ArrayList<SingleScaledCriterion>();

    public MultiScaledCriterion() {
    }

    public MultiScaledCriterion(MultiScaledCriterion multiScaledCriterion) {
        super(multiScaledCriterion);
        subCriterions = multiScaledCriterion.getSubCriterions();
    }

    @OneToMany(targetEntity = SingleScaledCriterion.class)
    public List<SingleScaledCriterion> getSubCriterions() {
        return subCriterions;
    }

    public void setSubCriterions(List<SingleScaledCriterion> subCriterions) {
        this.subCriterions = subCriterions;
    }
}
