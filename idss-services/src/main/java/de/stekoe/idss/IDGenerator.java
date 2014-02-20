package de.stekoe.idss;

import java.util.UUID;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class IDGenerator {
    private IDGenerator() {
        // NOP
    }

    public static String createId() {
        return UUID.randomUUID().toString();
    }
}
