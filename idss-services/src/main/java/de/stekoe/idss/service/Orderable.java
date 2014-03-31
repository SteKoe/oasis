package de.stekoe.idss.service;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface Orderable<T> {
    void move(T orderable, Direction direction);

    public static enum Direction {
        UP, DOWN;
    }
}
