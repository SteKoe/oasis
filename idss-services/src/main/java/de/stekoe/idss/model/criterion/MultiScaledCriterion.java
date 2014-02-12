package de.stekoe.idss.model.criterion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class MultiScaledCriterion extends Criterion {
    private List<SingleScaledCriterion> subCriterions = new ArrayList<SingleScaledCriterion>();

    public List<SingleScaledCriterion> getSubCriterions() {
        return subCriterions;
    }

    public void setSubCriterions(List<SingleScaledCriterion> subCriterions) {
        this.subCriterions = subCriterions;
    }
}
