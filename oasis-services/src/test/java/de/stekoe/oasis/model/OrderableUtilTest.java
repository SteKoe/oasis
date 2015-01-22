package de.stekoe.oasis.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import de.stekoe.oasis.model.OrderableUtil.Direction;

public class OrderableUtilTest {
    List<String> strings = Arrays.asList("A","B","C");

    @Test
    public void moveDownFirstElement() throws Exception {
        OrderableUtil.<String>move(strings, "A", Direction.DOWN);

        assertThat(strings.get(0), is(equalTo("B")));
        assertThat(strings.get(1), is(equalTo("A")));
        assertThat(strings.get(2), is(equalTo("C")));
    }

    @Test
    public void moveDownLastElement() throws Exception {
        // We expect this to be false since the last element can not be moved down
        OrderableUtil.<String>move(strings, "C", Direction.DOWN);

        assertThat(strings.get(0), is(equalTo("A")));
        assertThat(strings.get(1), is(equalTo("B")));
        assertThat(strings.get(2), is(equalTo("C")));
    }

    @Test
    public void moveNonExistingValue() throws Exception {
        List<String> old = strings;
        OrderableUtil.<String>move(strings, "D", Direction.DOWN);
        OrderableUtil.<String>move(strings, "D", Direction.UP);
        old.equals(strings);
    }

    @Test
    public void moveNullValue() throws Exception {
        List<String> old = strings;
        OrderableUtil.<String>move(strings, null, Direction.DOWN);
        old.equals(strings);
    }

    @Test
    public void moveUpLastElement() throws Exception {
        OrderableUtil.<String>move(strings, "C", Direction.UP);

        assertThat(strings.get(0), is(equalTo("A")));
        assertThat(strings.get(1), is(equalTo("C")));
        assertThat(strings.get(2), is(equalTo("B")));
    }

    @Test
    public void moveUpFirstElement() throws Exception {
        OrderableUtil.<String>move(strings, "A", Direction.UP);

        assertThat(strings.get(0), is(equalTo("A")));
        assertThat(strings.get(1), is(equalTo("B")));
        assertThat(strings.get(2), is(equalTo("C")));
    }
}
