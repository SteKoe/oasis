package de.stekoe.idss.model;

public enum EvaluationStatus implements L10NEnum {
    /*
     * Evaluation in vorbereitung
     * Evaluation in Testphase
     * Evaluation wird durchgeführt
     * Evaluation ist abgeschlossen
     */
    PREPARATION("status.evaluation.preparation"),
    TESTING("status.evaluation.testing"),
    INPROGRESS("status.evaluation.inprogress"),
    FINISHED("status.evaluation.finished");

    private String statusKey;

    EvaluationStatus(String statusKey) {
        this.statusKey = statusKey;
    }

    @Override
    public String getKey() {
        return this.statusKey;
    }
}
