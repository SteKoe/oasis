package de.stekoe.idss.model.enums;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum ProjectStatus implements L10NEnum {
    UNDEFINED("status.project.undefined"),
    UNKNOWN("status.project.unknown"),
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
