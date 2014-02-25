package de.stekoe.idss.model.criterion;

import de.stekoe.idss.model.criterion.scale.Scale;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class SingleScaledCriterion extends Criterion {
    private Scale scale;

    @OneToOne(targetEntity = Scale.class, cascade = CascadeType.ALL)
    public Scale getScale() {
        return scale;
    }

    public void setScale(Scale scale) {
        this.scale = scale;
    }
}
