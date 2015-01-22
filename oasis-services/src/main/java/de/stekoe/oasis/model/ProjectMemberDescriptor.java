package de.stekoe.oasis.model;

import java.io.Serializable;

public class ProjectMemberDescriptor implements Serializable {
    private String id;
    private String username;
    private String projectRoleName;

    public ProjectMemberDescriptor(String id, String username, String projectRoleName) {
        this.id = id;
        this.username = username;
        this.projectRoleName = projectRoleName;
    }

    public ProjectMemberDescriptor(ProjectMember projectMember) {
        this.setId(projectMember.getId());
        this.setUsername(projectMember.getUser().getUsername());
        this.setProjectRoleName(projectMember.getProjectRole().getName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProjectRoleName() {
        return projectRoleName;
    }

    public void setProjectRoleName(String projectRoleName) {
        this.projectRoleName = projectRoleName;
    }
}
