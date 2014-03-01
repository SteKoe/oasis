package de.stekoe.idss.model.criterion.scale.value;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MeasurementValueTest {

    private MeasurementValueTest.TestMeasurementValue itsA;
    private MeasurementValueTest.TestMeasurementValue itsB;
    private MeasurementValueTest.TestMeasurementValue itsA1;

    @Before
    public void setUp() {
        itsA = new TestMeasurementValue("A");
        itsA1 = new TestMeasurementValue("A");

        itsB = new TestMeasurementValue("B");
    }

    @Test
    public void testEquals() throws Exception {
        assertEquals(itsA, itsA1);
        assertNotEquals(itsA, itsB);
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(itsA.hashCode() == itsA1.hashCode());
        assertFalse(itsA.hashCode() == itsB.hashCode());
    }

    // Test Implementation of the class
    private class TestMeasurementValue extends MeasurementValue {
        protected TestMeasurementValue(String value) {
            super(value);
        }
    }
}
