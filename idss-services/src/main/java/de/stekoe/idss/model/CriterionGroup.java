package de.stekoe.idss.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class CriterionGroup extends PageElement {
    private List<Criterion> criterions = new ArrayList<Criterion>();

    public CriterionGroup() {
    }

    public CriterionGroup(CriterionGroup group) {
        super(group);

        for (Criterion c : group.getCriterions()) {
            if(c instanceof MultiScaledCriterion) {
                criterions.add(new MultiScaledCriterion((MultiScaledCriterion) c));
            } else if(c instanceof NominalScaledCriterion) {
                criterions.add(new NominalScaledCriterion((NominalScaledCriterion) c));
            } else if(c instanceof OrdinalScaledCriterion) {
                criterions.add(new OrdinalScaledCriterion((OrdinalScaledCriterion) c));
            }
        }
    }

    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Criterion.class)
    public List<Criterion> getCriterions() {
        return criterions;
    }
    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
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
}
