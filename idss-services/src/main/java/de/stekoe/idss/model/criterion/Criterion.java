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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public abstract class Criterion extends CriterionPageElement implements Serializable {

    private static final long serialVersionUID = 20141103925L;

    private String name;
    private String description;
    private boolean allowNoChoice = false;

    @NotNull
    @Column(nullable = false)
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

    @Column(columnDefinition = "boolean default false")
    public boolean isAllowNoChoice() {
        return allowNoChoice;
    }

    public void setAllowNoChoice(boolean aAllowNoChoice) {
        allowNoChoice = aAllowNoChoice;
    }
}
