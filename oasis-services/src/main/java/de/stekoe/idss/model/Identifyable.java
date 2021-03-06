package de.stekoe.idss.model;

import java.io.Serializable;

public interface Identifyable<T extends Serializable> {
    void setId(T id);
    T getId();
}
