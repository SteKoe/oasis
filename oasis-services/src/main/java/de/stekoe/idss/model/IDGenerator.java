package de.stekoe.idss.model;

import java.util.UUID;


public class IDGenerator {
    private IDGenerator() {
        // NOP
    }

    public static String createId() {
        return UUID.randomUUID().toString();
    }
}
