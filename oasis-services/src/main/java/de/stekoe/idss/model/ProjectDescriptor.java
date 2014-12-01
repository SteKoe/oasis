package de.stekoe.idss.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class ProjectDescriptor {
    private String id = IDGenerator.createId();
    private String name;
    private String description;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}
