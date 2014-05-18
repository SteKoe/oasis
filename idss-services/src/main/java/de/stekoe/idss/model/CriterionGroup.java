package de.stekoe.idss.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class CriterionGroup extends PageElement {
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
        for (Criterion c : getCriterions()) {
            c.getCriterionGroups().remove(this);
        }
    }

    /**
     * If you want to add new Criterions to this CriterionGroup use
     * {@code CriterionGroup#addCriterion(Criterion)} instead!
     * @return
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, targetEntity = Criterion.class)
    public List<Criterion> getCriterions() {
        return criterions;
    }
    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public void addCriterion(Criterion criterion) {
        if(!getCriterions().contains(criterion)) {
            getCriterions().add(criterion);
            criterion.addCriterionGroup(this);
        }
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
    public String toString() {
        return new ToStringBuilder(this)
            .append(super.toString())
            .append("criterions", getCriterions())
            .toString();
    }
}
