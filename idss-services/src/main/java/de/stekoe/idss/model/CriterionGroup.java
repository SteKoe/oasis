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

}
