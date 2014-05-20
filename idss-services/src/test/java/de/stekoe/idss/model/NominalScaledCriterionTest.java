package de.stekoe.idss.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class NominalScaledCriterionTest {
    @Test
    public void testCopyConstructor() throws Exception {

        NominalScaledCriterion scale = new NominalScaledCriterion();
        scale.setName("Criterion");
        scale.setDescription("Description");
        scale.setAllowNoChoice(true);
        scale.setMultipleChoice(true);

        NominalValue value = new NominalValue("A");
        scale.addValue(value);
        value = new NominalValue("B");
        scale.addValue(value);
        value = new NominalValue("C");
        scale.addValue(value);

        NominalScaledCriterion scaleCopy = new NominalScaledCriterion(scale);

        assertThat(scaleCopy.getId(), not(equalTo(scale.getId())));
        assertThat(scaleCopy.getName(), equalTo(scale.getName()));
        assertThat(scaleCopy.getDescription(), equalTo(scale.getDescription()));
        assertThat(scaleCopy.isAllowNoChoice(), equalTo(scale.isAllowNoChoice()));
        assertThat(scaleCopy.isMultipleChoice(), equalTo(scale.isMultipleChoice()));

        assertThat(scaleCopy.getValues().size(), is(equalTo(scale.getValues().size())));

        for(NominalValue nominalValue : scaleCopy.getValues()) {
            String id = nominalValue.getId();
            for(NominalValue nominalValue2 : scale.getValues()) {
                assertThat(id, is(not(equalTo(nominalValue2.getId()))));
            }
        }
    }
}
