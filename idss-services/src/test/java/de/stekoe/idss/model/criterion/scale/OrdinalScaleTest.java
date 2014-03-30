package de.stekoe.idss.model.criterion.scale;

import java.util.List;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class OrdinalScaleTest {

    private OrdinalScale ordinalScale;

    @Before
    public void setUp() {
        ordinalScale = new OrdinalScale();
        ordinalScale.getValues().add(new OrdinalValue(1, "sehr gut"));
        ordinalScale.getValues().add(new OrdinalValue(2, "gut"));
        ordinalScale.getValues().add(new OrdinalValue(3, "befriedingend"));
        ordinalScale.getValues().add(new OrdinalValue(4, "ausreichend"));
        ordinalScale.getValues().add(new OrdinalValue(5, "mangelhaft"));
        ordinalScale.getValues().add(new OrdinalValue(6, "ungenügend"));
    }

    @Test
    public void testValidValue() throws Exception {
        Assert.assertTrue(ordinalScale.isValid(new OrdinalValue(1, "sehr gut")));
    }

    @Test
    public void testInvalidValue() throws Exception {
        Assert.assertFalse(ordinalScale.isValid(new OrdinalValue(3, "intersexuell")));
    }

    @Test
    public void testOrdering() throws Exception {
        final List<OrdinalValue> values = ordinalScale.getValues();

        Assert.assertThat(values.get(0).getValue(), IsEqual.equalTo("sehr gut"));
        Assert.assertThat(values.get(1).getValue(), IsEqual.equalTo("gut"));
        Assert.assertThat(values.get(2).getValue(), IsEqual.equalTo("befriedingend"));
        Assert.assertThat(values.get(3).getValue(), IsEqual.equalTo("ausreichend"));
        Assert.assertThat(values.get(4).getValue(), IsEqual.equalTo("mangelhaft"));
        Assert.assertThat(values.get(5).getValue(), IsEqual.equalTo("ungenügend"));
    }

    @Test
    public void valueIsGreater() throws Exception {
        final OrdinalValue val1 = new OrdinalValue(1, "sehr gut");
        final OrdinalValue val2 = new OrdinalValue(2, "gut");

        Assert.assertTrue(val1.isLowerThan(val2));
    }

    @Test
    public void valueIsLower() throws Exception {
        final OrdinalValue val1 = new OrdinalValue(1, "sehr gut");
        final OrdinalValue val2 = new OrdinalValue(2, "gut");

        Assert.assertTrue(val2.isGreaterThan(val1));
    }

}
