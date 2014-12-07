package de.stekoe.idss.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class MultiScaledCriterion extends Criterion {
    private static final long serialVersionUID = 201404132234L;

    private List<SingleScaledCriterion> subCriterions = new ArrayList<SingleScaledCriterion>();

    public MultiScaledCriterion() {
    }

    public MultiScaledCriterion(MultiScaledCriterion multiScaledCriterion) {
        super(multiScaledCriterion);
        subCriterions = multiScaledCriterion.getSubCriterions();
    }

    @OneToMany(targetEntity = SingleScaledCriterion.class)
    public List<SingleScaledCriterion> getSubCriterions() {
        return subCriterions;
    }

    public void setSubCriterions(List<SingleScaledCriterion> subCriterions) {
        this.subCriterions = subCriterions;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof MultiScaledCriterion)) return false;

        MultiScaledCriterion that = (MultiScaledCriterion) other;
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
