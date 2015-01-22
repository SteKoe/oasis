package de.stekoe.oasis.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public abstract class Criterion extends PageElement implements Serializable {

    private static final long serialVersionUID = 20141205L;

    private boolean allowNoChoice = false;
    private int weight = 1;
    private CriterionGroup criterionGroup;

    public Criterion() {
        // NOP
    }

    public Criterion(Criterion criterion) {
        super(criterion);
        this.allowNoChoice = criterion.isAllowNoChoice();
    }

    @Column
    public boolean isAllowNoChoice() {
        return allowNoChoice;
    }
    public void setAllowNoChoice(boolean aAllowNoChoice) {
        allowNoChoice = aAllowNoChoice;
    }

    @Column
    public int getWeight() {
        return this.weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @ManyToOne
    public CriterionGroup getCriterionGroup() {
        return criterionGroup;
    }
    public void setCriterionGroup(CriterionGroup criterionGroup) {
        this.criterionGroup = criterionGroup;
    }

    /**
     * Copies the given criterion.
     *
     * @param criterion The criterion to copy
     * @return The copied criterion or null on failure
     */
    public static Criterion copyCriterion(Criterion criterion) {
        if (criterion instanceof MultiScaledCriterion) {
            return new MultiScaledCriterion((MultiScaledCriterion) criterion);
        } else if (criterion instanceof NominalScaledCriterion) {
            return new NominalScaledCriterion((NominalScaledCriterion) criterion);
        } else if (criterion instanceof OrdinalScaledCriterion) {
            return new OrdinalScaledCriterion((OrdinalScaledCriterion) criterion);
        }

        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Criterion)) return false;

        Criterion that = (Criterion) other;
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
