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
