package de.stekoe.idss.service;

public interface Orderable<T> {
    void move(T orderable, Direction direction);

    public static enum Direction {
        UP, DOWN;
    }
}
