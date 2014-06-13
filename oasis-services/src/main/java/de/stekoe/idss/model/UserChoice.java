package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_user_id","criterion_id"})})
public class UserChoice implements Serializable {
    private static final long serialVersionUID = 201404132212L;

    private String id = IDGenerator.createId();
    private User user;
    private Project project;
    private SingleScaledCriterion criterion;
    private List<MeasurementValue> measurementValues = new ArrayList<MeasurementValue>();

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

    @ElementCollection(targetClass = MeasurementValue.class)
    public List<MeasurementValue> getMeasurementValues() {
        return measurementValues;
    }
    public void setMeasurementValues(List<MeasurementValue> measurementValues) {
        this.measurementValues = measurementValues;
    }

    @ManyToOne(targetEntity = SingleScaledCriterion.class)
    public SingleScaledCriterion<MeasurementValue> getCriterion() {
        return criterion;
    }
    public void setCriterion(SingleScaledCriterion<? extends MeasurementValue> criterion) {
        this.criterion = criterion;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof UserChoice)) return false;

        UserChoice that  = (UserChoice) other;
        return new EqualsBuilder()
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
