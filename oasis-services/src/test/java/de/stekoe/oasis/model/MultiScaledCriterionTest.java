package de.stekoe.oasis.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Stephan Koeninger 
 */
public class MultiScaledCriterionTest {
    @Test
    public void asd() throws Exception {
        final SingleScaledCriterion ssCriterion1 = new OrdinalScaledCriterion();
        ssCriterion1.setName("SSC1");

        final SingleScaledCriterion ssCriterion2 = new OrdinalScaledCriterion();
        ssCriterion2.setName("SSC2");

        final SingleScaledCriterion ssCriterion3 = new OrdinalScaledCriterion();
        ssCriterion3.setName("SSC3");

        final MultiScaledCriterion multiScaledCriterion = new MultiScaledCriterion();
        multiScaledCriterion.getSubCriterions().add(ssCriterion1);
        multiScaledCriterion.getSubCriterions().add(ssCriterion2);
        multiScaledCriterion.getSubCriterions().add(ssCriterion3);

        assertTrue(multiScaledCriterion.getSubCriterions().get(0).getName().equals("SSC1"));
        assertTrue(multiScaledCriterion.getSubCriterions().get(1).getName().equals("SSC2"));
        assertTrue(multiScaledCriterion.getSubCriterions().get(2).getName().equals("SSC3"));
    }
}
