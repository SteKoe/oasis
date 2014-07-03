package de.stekoe.idss.model;


public enum CriterionMetric implements L10NEnum {
    NOMINAL("label.criterion.nominal"),
    ORDINAL("label.criterion.ordinal"),
    METRIC("label.criterion.metric");

    private String statusKey;

    CriterionMetric(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getKey() {
        return this.statusKey;
    }
}
