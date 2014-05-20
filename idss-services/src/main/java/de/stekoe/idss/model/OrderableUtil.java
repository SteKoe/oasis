package de.stekoe.idss.model;

import java.util.List;

public class OrderableUtil<T> {

    public enum Direction {
        UP, DOWN;
    }

    private OrderableUtil() {
        // NOP
    }

    public static <T> boolean move(List<T> list, T value, Direction direction) {
        if(value != null) {
            List<T> reorderedValues = list;
            int index = reorderedValues.indexOf(value);

            if(index >= 0) {
                int otherIndex;
                if(Direction.UP.equals(direction)) {
                    otherIndex = index - 1;
                    if(otherIndex < 0) {
                        return false;
                    }
                } else {
                    otherIndex = index + 1;
                    if(otherIndex >= list.size()) {
                        return false;
                    }
                }

                T otherValue = reorderedValues.get(otherIndex);

                reorderedValues.set(index, otherValue);
                reorderedValues.set(otherIndex, value);

                list = reorderedValues;
                return true;
            }
        }

        return false;
    }
}
