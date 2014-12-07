package de.stekoe.idss.model;

import de.stekoe.idss.model.OrderableUtil.Direction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class CriterionGroup extends PageElement {
    private static final long serialVersionUID = 20141205;

    private List<Criterion> criterions = new ArrayList<>();

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
     * @param group          The CriterionGroup to copy
     * @param copyCriterions true if one wants to copy the assicated criterions, false otherwise
     */
    public CriterionGroup(CriterionGroup group, boolean copyCriterions) {
        super(group);
        if (copyCriterions) {
            for (Criterion c : group.getCriterions()) {
                criterions.add(Criterion.copyCriterion(c));
            }
        }
    }

    @OneToMany(mappedBy = "criterionGroup", cascade = CascadeType.ALL, orphanRemoval = true)
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
     * @param criteriaGroup      The CriterionGroup to copy
     * @param selectedCriterions Subset list of criterions to copy
     * @return A full copy of the CriterionGroup excluding the non selected criterions
     */
    public static CriterionGroup selectiveCopy(CriterionGroup criteriaGroup, List<String> selectedCriterions) {
        CriterionGroup copy = new CriterionGroup(criteriaGroup);
        List<Criterion> copiedCriterions = copy.getCriterions();

        List<Criterion> collect = copiedCriterions.stream()
                .filter(criterion -> selectedCriterions.contains(criterion.getOriginId()))
                .collect(Collectors.toList());

        for(Criterion c : collect) {
            c.setCriterionGroup(copy);
            System.out.println();
        }
        copy.setCriterions(collect);

        return copy;
    }
}
