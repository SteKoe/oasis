package de.stekoe.idss.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.PreRemove;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import de.stekoe.idss.model.OrderableUtil.Direction;

@Entity
public class CriterionGroup extends PageElement {
    private static final long serialVersionUID = 201405301330L;

    private List<Criterion> criterions = new ArrayList<Criterion>();

    public CriterionGroup() {
        // NOP
    }

    /**
     * Copy constructor.
     * Copies the whole CriterionGroup including the associated criterions.
     * If you just want to copy the CriterionGroup only without its criterions, use CriterionGroup(CriterionGroup, Boolean)
     * instead.
     *
     * @param group The CriterionGroup object to clone
     */
    public CriterionGroup(CriterionGroup group) {
        this(group, true);
    }

    /**
     * Copy constructor.
     * Creates a flat copy of the given CriterionGroup if copyCriterions is false. Flat copy means that no associated
     * criterions will be cloned. If argument copyCriterions is true, all associated criterions will be copied as well.
     *
     * @param group             The CriterionGroup to copy
     * @param copyCriterions    true if one wants to copy the assicated criterions, false otherwise
     */
    public CriterionGroup(CriterionGroup group, boolean copyCriterions) {
        super(group);
        if(copyCriterions) {
            for (Criterion c : group.getCriterions()) {
                criterions.add(Criterion.copyCriterion(c));
            }
        }
    }

    @PreRemove
    private void removeCriterionGroupFromCriterions() {
        Iterator<Criterion> criterionIterator = getCriterions().iterator();
        while(criterionIterator.hasNext()) {
            Iterator<CriterionGroup> iterator = criterionIterator.next().getCriterionGroups().iterator();
            while(iterator.hasNext()) {
                if(this.equals(iterator.next())) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * If you want to add new Criterions to this CriterionGroup use
     * {@code CriterionGroup#addCriterion(Criterion)} instead!
     * @return
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, targetEntity = Criterion.class)
    @OrderColumn(name = "ordering")
    public List<Criterion> getCriterions() {
        return criterions;
    }
    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public boolean move(Criterion criterion, Direction direction) {
        List<Criterion> old = criterions;
        OrderableUtil.<Criterion>move(criterions, criterion, direction);
        return old.equals(criterions);
    }

    /**
     * This method allows to copy a subset of a CriterionGroup.
     * E.g. if someone wants to have all but one Criterions of a particular CriterionGroup, one just passes all Criterions
     * into this method which will be included in the copy.
     *
     * @param criteriaGroup            The CriterionGroup to copy
     * @param selectedCriterions       Subset list of criterions to copy
     * @return A full copy of the CriterionGroup excluding the non selected criterions
     */
    public static CriterionGroup selectiveCopy(CriterionGroup criteriaGroup, List<Criterion> selectedCriterions) {
        CriterionGroup copy = new CriterionGroup(criteriaGroup);
        List<Criterion> copiedCriterions = copy.getCriterions();

        for (Criterion copiedCriterion : copiedCriterions) {
            boolean isSelected = false;
            for(Criterion selectedCriterion : selectedCriterions) {
                if(copiedCriterion.getOriginId().equals(selectedCriterion.getId())) {
                    isSelected = true;
                    break;
                }
            }

            if(!isSelected) {
                copiedCriterions.remove(copiedCriterion);
            }
        }
        return copy;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof CriterionGroup)) return false;

        CriterionGroup that  = (CriterionGroup) other;
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
