package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserChoice implements Serializable {
    private static final long serialVersionUID = 201404132212L;

    private String id = IDGenerator.createId();
    private User user;
    private Project project;
    private MeasurementValue measurementValue;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = Project.class)
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    @ManyToOne(targetEntity = MeasurementValue.class)
    public MeasurementValue getMeasurementValue() {
        return measurementValue;
    }
    public void setMeasurementValue(MeasurementValue measurementValue) {
        this.measurementValue = measurementValue;
    }
}
