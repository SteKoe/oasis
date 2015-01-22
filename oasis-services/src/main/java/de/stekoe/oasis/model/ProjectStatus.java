package de.stekoe.oasis.model;


public enum ProjectStatus implements L10NEnum {
    EDITING("status.project.editing"),
    INPROGRESS("status.project.inprogress"),
    FINISHED("status.project.finished"),
    CANCELED("status.project.canceled");

    private String statusKey;

    ProjectStatus(String statusKey) {
        this.statusKey = statusKey;
    }

    @Override
    public String getKey() {
        return this.statusKey;
    }
}
