package de.stekoe.idss.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.OrdinalScale;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

public class DefaultCriterionServiceTest extends AbstractBaseTest {
    @Test
    public void addNewValue() {
        SingleScaledCriterion criterion = new SingleScaledCriterion();
        criterion.setScale(new OrdinalScale());

        OrdinalValue value = new OrdinalValue(1, "A");

        List<OrdinalValue> values = (List<OrdinalValue>) criterion.getScale().getValues();
        values.add(value);

        assertTrue(criterion.getScale().getValues().contains(value));
    }
}
