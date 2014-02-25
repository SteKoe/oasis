package de.stekoe.idss.model.criterion;

import org.junit.Test;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SingleScaledCriterionTest {
    @Test
    public void newSingleScaledCriterion() throws Exception {
        final SingleScaledCriterion ssc = new SingleScaledCriterion();
        ssc.setName("Name");
        ssc.setDescription("Description");
    }
}
