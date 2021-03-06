package de.stekoe.idss.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import de.stekoe.idss.model.OrderableUtil.Direction;

@Entity
public abstract class SingleScaledCriterion<T extends MeasurementValue> extends Criterion {

    private static final long serialVersionUID = 201403181647L;

    private List values = new ArrayList();

    public SingleScaledCriterion() {
    }

    public SingleScaledCriterion(SingleScaledCriterion<T> singleScaledCriterion) {
        super(singleScaledCriterion);

        for(Object mv : singleScaledCriterion.getValues()) {
            if(mv instanceof OrdinalValue) {
                OrdinalValue val = new OrdinalValue((OrdinalValue) mv);
                values.add(val);
                val.setCriterion(this);
            } else if(mv instanceof NominalValue) {
                NominalValue val = new NominalValue((NominalValue) mv);
                values.add(val);
                val.setCriterion(this);
            }
        }
    }

    @OneToMany(mappedBy = "criterion", targetEntity = MeasurementValue.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<T> getValues() {
        return this.values;
    }
    public void setValues(List<T> values) {
        this.values = values;
    }

    @Transient
    public List<T> move(T value, Direction direction) {
        return OrderableUtil.<T>move(values, value, direction);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof SingleScaledCriterion)) return false;

        SingleScaledCriterion that  = (SingleScaledCriterion) other;
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
