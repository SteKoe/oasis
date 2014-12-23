package de.stekoe.oasis.web.reports;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.UserChoices;

import java.util.Arrays;
import java.util.List;

/**
 * Creates a report for one single criterion
 *
 * @param <T> Type of the result
 */
public abstract class SingleCriterionReport<T> extends MultiCriterionReport<T> {
    public SingleCriterionReport(Criterion criterion, List<UserChoices> userChoiceses) {
        super(Arrays.asList(criterion), userChoiceses);
    }

    protected Criterion getCriterion() {
        return this.getCriterions().get(0);
    }
}
