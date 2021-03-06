package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MeasurementValue implements Serializable {
    private static final long serialVersionUID = 201404132234L;

    private String id = IDGenerator.createId();
    private String value;
    private SingleScaledCriterion criterion;

    protected MeasurementValue() {
        // NOP
    }

    protected MeasurementValue(String value) {
        this.value = value;
    }

    public MeasurementValue(MeasurementValue measurementValue) {
        value = measurementValue.getValue();
        criterion = measurementValue.getCriterion();
    }

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne(targetEntity = SingleScaledCriterion.class)
    @OrderColumn(name = "ordering")
    public SingleScaledCriterion getCriterion() {
        return criterion;
    }
    public void setCriterion(SingleScaledCriterion criterion) {
        this.criterion = criterion;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof MeasurementValue)) return false;

        MeasurementValue that  = (MeasurementValue) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
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
