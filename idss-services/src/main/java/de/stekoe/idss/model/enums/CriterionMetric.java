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

package de.stekoe.idss.model.enums;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum CriterionMetric implements L10NEnum {
    NOMINAL("label.criterion.nominal"),
    ORDINAL("label.criterion.ordinal"),
    METRIC("label.criterion.metric");

    private String statusKey;

    CriterionMetric(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getKey() {
        return this.statusKey;
    }
}
