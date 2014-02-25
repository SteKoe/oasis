package de.stekoe.idss.model.criterion;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
public class MultiScaledCriterion extends Criterion {
    private List<SingleScaledCriterion> subCriterions = new ArrayList<SingleScaledCriterion>();

    @OneToMany(targetEntity = SingleScaledCriterion.class)
    public List<SingleScaledCriterion> getSubCriterions() {
        return subCriterions;
    }

    public void setSubCriterions(List<SingleScaledCriterion> subCriterions) {
        this.subCriterions = subCriterions;
    }
}
