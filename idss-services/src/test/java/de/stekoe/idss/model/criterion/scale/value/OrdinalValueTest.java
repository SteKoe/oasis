package de.stekoe.idss.model.criterion.scale.value;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrdinalValueTest {

    private OrdinalValue a;
    private OrdinalValue b;

    @Before
    public void setUp() {
        a = new OrdinalValue(1, "A");
        b = new OrdinalValue(2, "B");
    }

    @Test
    public void isGreaterThanIsTrue() throws Exception {
        assertTrue(b.isGreaterThan(a));
    }

    @Test
    public void isGreaterThanIsFalse() throws Exception {
        assertFalse(a.isGreaterThan(b));
    }

    @Test
    public void isLowerThanIsTrue() throws Exception {
        assertTrue(a.isLowerThan(b));
    }

    @Test
    public void isLowerThanIsFalse() throws Exception {
        assertFalse(b.isLowerThan(a));
    }
}