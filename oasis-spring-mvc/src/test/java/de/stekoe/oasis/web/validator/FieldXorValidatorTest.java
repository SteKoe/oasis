package de.stekoe.oasis.web.validator;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FieldXorValidatorTest {

    @Test
    public void isValidIsFalseForABNull() throws Exception {
        FieldXorValidatorMock mock = new FieldXorValidatorMock();
        boolean actual = mock.checkIsValid(null, null);
        assertThat(actual, is(false));
    }

    @Test
    public void isValidIsFalseForABEqual() throws Exception {
        FieldXorValidatorMock mock = new FieldXorValidatorMock();
        boolean actual = mock.checkIsValid("A", "A");
        assertThat(actual, is(false));
    }

    @Test
    public void isValidIsFalseForABNotEqual() throws Exception {
        FieldXorValidatorMock mock = new FieldXorValidatorMock();
        boolean actual = mock.checkIsValid("A", "B");
        assertThat(actual, is(false));
    }

    @Test
    public void isValidIsTrueForAhavingValueBnoValue() throws Exception {
        FieldXorValidatorMock mock = new FieldXorValidatorMock();
        boolean actual = mock.checkIsValid("A", null);
        assertThat(actual, is(true));
    }

    @Test
    public void isValidIsTrueForAnoValueBhavingValue() throws Exception {
        FieldXorValidatorMock mock = new FieldXorValidatorMock();
        boolean actual = mock.checkIsValid(null, "B");
        assertThat(actual, is(true));
    }

    class FieldXorValidatorMock extends FieldXorValidator {
    }
}