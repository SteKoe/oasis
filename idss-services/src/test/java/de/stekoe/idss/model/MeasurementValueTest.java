package de.stekoe.idss.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.stekoe.idss.model.MeasurementValue;

public class MeasurementValueTest {

    private MeasurementValueTest.TestMeasurementValue itsA;
    private MeasurementValueTest.TestMeasurementValue itsB;
    private MeasurementValueTest.TestMeasurementValue itsA1;

    @Before
    public void setUp() {
        itsA = new TestMeasurementValue(1, "A");
        itsA1 = new TestMeasurementValue(2, "A");

        itsB = new TestMeasurementValue(3, "B");
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
        protected TestMeasurementValue(int rank, String value) {
            super(rank, value);
        }
    }
}
