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


/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum ProjectStatus implements L10NEnum {
    EDITING("status.project.editing"),
    INPROGRESS("status.project.inprogress"),
    FINISHED("status.project.finished"),
    CANCELED("status.project.canceled");

    private String statusKey;

    ProjectStatus(String statusKey) {
        this.statusKey = statusKey;
    }

    @Override
    public String getKey() {
        return this.statusKey;
    }
}
