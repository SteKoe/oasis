package de.stekoe.idss.service.impl;

import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;
import de.stekoe.idss.service.CriterionService;

public class DefaultCriterionServiceTest extends AbstractBaseTest {

    @Inject
    private CriterionService criterionService;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() {
        SingleScaledCriterion criterion = new SingleScaledCriterion();
        criterion.setScale(new OrdinalScale());

        OrdinalValue value = new OrdinalValue(1, "A");
        criterionService.addValue(criterion, value);

        SingleScaledCriterion crit = criterionService.findSingleScaledCriterionById(criterion.getId());
        assertTrue(crit.getScale().getValues().contains(value));
    }

}
