package de.stekoe.idss.page.project.criterion;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.Model;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;

class SelectReferenceCriterionModel extends Model<Boolean> {

    private final CriterionGroup criterionGroup;
    private final Criterion criterion;
    private final List<CriterionGroup> selectedCriterionGroups;

    public SelectReferenceCriterionModel(CriterionGroup criterionGroup, Criterion criterion, List<CriterionGroup> selectedCriterionGroups) {
        this.criterionGroup = criterionGroup;
        this.criterion = criterion;
        this.selectedCriterionGroups = selectedCriterionGroups;
    }

    /**
     * Looks for a CriterionGroup in the list of selected CriterionGroups having an originId equal to the given id.
     *
     * @param id        The id of the orginial CriterionGroup
     * @return          CriterionGroup if the list contains a copied CriterionGroup with the given id, null otherwise
     */
    private CriterionGroup findCopyOfCriterionGroupInSelectedList(String id) {
        for(CriterionGroup selectedCriterionGroup : getSelectedCriterionGroups()) {
            if(selectedCriterionGroup.getOriginId().equals(id)) {
                return selectedCriterionGroup;
            }
        }

        return null;
    }

    @Override
    public void setObject(Boolean isSelected) {
        CriterionGroup selectedCriterionGroup = findCopyOfCriterionGroupInSelectedList(criterionGroup.getId());

        if(isSelected) {
            if(selectedCriterionGroup != null) {
                Criterion copiedCriterion = Criterion.copyCriterion(criterion);
                selectedCriterionGroup.getCriterions().add(copiedCriterion);
            } else {
                // Create a copy of the selected reference CriterionGroup and add a copy of the criterion to it.
                CriterionGroup copiedCriterionGroup = new CriterionGroup(criterionGroup, false);
                getSelectedCriterionGroups().add(copiedCriterionGroup);

                // Creating the copy of the criterion
                Criterion copiedCriterion = Criterion.copyCriterion(criterion);
                copiedCriterionGroup.getCriterions().add(copiedCriterion);

            }
        } else {
            if(selectedCriterionGroup != null) {
                List<Criterion> selectedCriterions = selectedCriterionGroup.getCriterions();

                // Remove the unselected Criterion from the list of selected criterions
                List<Criterion> result = new ArrayList<Criterion>(selectedCriterions);
                for(Criterion selectedCriterion : selectedCriterions) {
                    if(selectedCriterion.getOriginId().equals(criterion.getId())) {
                        result.remove(selectedCriterion);
                    }
                }
                selectedCriterionGroup.setCriterions(result);

                /*
                 * If the selected CriterionGroup is empty after removing the Criterion, remove it
                 * from the selection list
                 */
                if(selectedCriterionGroup.getCriterions().size() == 0) {
                    getSelectedCriterionGroups().remove(selectedCriterionGroup);
                }
            }
        }
    }

    @Override
    public Boolean getObject() {
        for(CriterionGroup selectedCriterionGroup : getSelectedCriterionGroups()) {
            if(selectedCriterionGroup.getOriginId().equals(criterionGroup.getId())) {
                return selectedCriterionGroup.getCriterions().contains(criterion);
            }
        }

        return false;
    }

    private List<CriterionGroup> getSelectedCriterionGroups() {
        return selectedCriterionGroups;
    }
}