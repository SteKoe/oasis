package de.stekoe.idss.model;

public enum NameSuffix implements L10NEnum {
    DR("name.suffix.dr"),
    PROF("name.suffix.prof"),
    PROFDR("name.suffix.profdr"),
    PRIVDOZ("name.suffix.privdoz"),
    PRIVDOZDR("name.suffix.privdozdr");

    private final String key;

    NameSuffix(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}
