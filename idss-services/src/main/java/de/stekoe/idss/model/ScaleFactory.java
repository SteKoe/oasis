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

package de.stekoe.idss.model;

import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ScaleFactory {

    private ScaleFactory() {
        // NOP
    }

    public static OrdinalScale createEvenLikertScale() {
        final OrdinalScale likertScale = new OrdinalScale();
        likertScale.getValues().add(new OrdinalValue(1, "scale.likert.agree.totally"));
        likertScale.getValues().add(new OrdinalValue(2, "scale.likert.agree"));
        likertScale.getValues().add(new OrdinalValue(3, "scale.likert.neutral"));
        likertScale.getValues().add(new OrdinalValue(4, "scale.likert.disagree"));
        likertScale.getValues().add(new OrdinalValue(5, "scale.likert.disagree.totally"));
        return likertScale;
    }

    public static OrdinalScale createOddLikertScale() {
        final OrdinalScale likertScale = new OrdinalScale();
        likertScale.getValues().add(new OrdinalValue(1, "scale.likert.agree.totally"));
        likertScale.getValues().add(new OrdinalValue(2, "scale.likert.agree"));
        likertScale.getValues().add(new OrdinalValue(3, "scale.likert.disagree"));
        likertScale.getValues().add(new OrdinalValue(4, "scale.likert.disagree.totally"));
        return likertScale;
    }
}
