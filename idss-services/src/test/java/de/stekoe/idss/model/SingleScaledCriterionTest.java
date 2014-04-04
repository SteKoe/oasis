package de.stekoe.idss.model;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.SingleScaledCriterion;

public class SingleScaledCriterionTest {

    private SingleScaledCriterion<NominalValue> ssc;

    @Before
    public void setUp() {
        ssc = new NominalScaledCriterion();
        ssc.setName("Name");
        ssc.setDescription("Description");
    }


    @Test
    public void addValue() throws Exception {
        assertThat(ssc.getNextValueOrdering(), IsEqual.equalTo(0));

        ssc.addValue(new NominalValue());
        assertThat(ssc.getNextValueOrdering(), IsEqual.equalTo(1));

        ssc.addValue(new NominalValue());
        assertThat(ssc.getNextValueOrdering(), IsEqual.equalTo(2));
    }

    @Test
    public void getValueWithOrdering() throws Exception {
        NominalValue value1 = new NominalValue();
        NominalValue value2 = new NominalValue();

        ssc.addValue(value1);
        ssc.addValue(value2);

        assertThat(ssc.getValueWithOrdering(1), IsEqual.equalTo(value2));
    }

    @Test
    public void moveUp() throws Exception {
        NominalValue value1 = new NominalValue("A");
        NominalValue value2 = new NominalValue("B");
        NominalValue value3 = new NominalValue("C");

        ssc.addValue(value1);
        ssc.addValue(value2);
        ssc.addValue(value3);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));
        assertThat(value3.getOrdering(), IsEqual.equalTo(2));

        ssc.moveUp(value2);

        assertThat(value1.getOrdering(), IsEqual.equalTo(1));
        assertThat(value2.getOrdering(), IsEqual.equalTo(0));
        assertThat(value3.getOrdering(), IsEqual.equalTo(2));
    }

    @Test
    public void moveUpFirstElementWillFail() throws Exception {
        NominalValue value1 = new NominalValue();
        NominalValue value2 = new NominalValue();

        ssc.addValue(value1);
        ssc.addValue(value2);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));

        ssc.moveUp(value1);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));
    }

    @Test
    public void moveDown() throws Exception {
        NominalValue value1 = new NominalValue("A");
        NominalValue value2 = new NominalValue("B");
        NominalValue value3 = new NominalValue("C");

        ssc.addValue(value1);
        ssc.addValue(value2);
        ssc.addValue(value3);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));
        assertThat(value3.getOrdering(), IsEqual.equalTo(2));

        ssc.moveDown(value2);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(2));
        assertThat(value3.getOrdering(), IsEqual.equalTo(1));
    }

    @Test
    public void moveDownLastElementWillFail() throws Exception {
        NominalValue value1 = new NominalValue("A");
        NominalValue value2 = new NominalValue("B");

        ssc.addValue(value1);
        ssc.addValue(value2);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));

        ssc.moveDown(value2);

        assertThat(value1.getOrdering(), IsEqual.equalTo(0));
        assertThat(value2.getOrdering(), IsEqual.equalTo(1));
    }
}
