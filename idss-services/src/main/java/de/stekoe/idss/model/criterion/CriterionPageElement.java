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

package de.stekoe.idss.model.criterion;

import de.stekoe.idss.IDGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CriterionPageElement implements Serializable {
    private CriterionPageElementId id = new CriterionPageElementId();
    private CriterionPage criterionPage;
    private int ordering;

    @EmbeddedId
    @AttributeOverride(name = "id", column = @Column(name = "criterion_page_element_id"))
    public CriterionPageElementId getId() {
        return id;
    }

    public void setId(CriterionPageElementId id) {
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
}
