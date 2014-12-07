package de.stekoe.oasis.web.project.criterions;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
class ReferenceCriterionSelection {
    private Map<String, List<String>> selectedReferenceCriterions = new HashMap<>();

    public Map<String, List<String>> getSelectedReferenceCriterions() {
        return selectedReferenceCriterions;
    }

    public void setSelectedReferenceCriterions(Map<String, List<String>> selectedReferenceCriterions) {
        this.selectedReferenceCriterions = selectedReferenceCriterions;
    }
}
