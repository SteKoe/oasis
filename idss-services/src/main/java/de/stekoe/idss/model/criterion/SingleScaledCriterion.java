package de.stekoe.idss.model.criterion;

import de.stekoe.idss.model.scale.Scale;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SingleScaledCriterion extends Criterion {
    private Scale scale;

    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }
}
