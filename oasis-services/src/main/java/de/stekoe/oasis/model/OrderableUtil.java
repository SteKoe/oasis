package de.stekoe.oasis.model;

import java.util.List;

public class OrderableUtil<T> {

    public enum Direction {
        UP, DOWN;
    }

    private OrderableUtil() {
        // NOP
    }

    public static <T> List<T> move(List<T> list, T value, Direction direction) {
        if (value != null) {
            List<T> reorderedValues = list;
            int index = reorderedValues.indexOf(value);

            if (index >= 0) {
                int otherIndex;
                if (Direction.UP.equals(direction)) {
                    otherIndex = index - 1;
                    if (otherIndex < 0) {
                        return list;
                    }
                } else {
                    otherIndex = index + 1;
                    if (otherIndex >= list.size()) {
                        return list;
                    }
                }

                T otherValue = reorderedValues.get(otherIndex);

                reorderedValues.set(index, otherValue);
                reorderedValues.set(otherIndex, value);

                list = reorderedValues;
                return list;
            }
        }

        return list;
    }
}
