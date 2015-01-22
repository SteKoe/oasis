package de.stekoe.oasis.model;


public enum CriterionType implements L10NEnum {
    ORDINAL("label.criterion.type.ordinal"),
    NOMINAL("label.criterion.type.nominal"),
    METRIC("label.criterion.type.metric");

    private String statusKey;

    CriterionType(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getKey() {
        return this.statusKey;
    }
}
