package de.stekoe.idss.model.enums;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum ProjectStatus {
    EDITING("status.project.editing"),
    INPPROGRESS("status.project.inprogress"),
    CANCELED("status.project.canceled");
    private String statusKey;

    ProjectStatus(String statusKey) {
        this.statusKey = statusKey;
    }

    public String getKey() {
        return this.statusKey;
    }
}
