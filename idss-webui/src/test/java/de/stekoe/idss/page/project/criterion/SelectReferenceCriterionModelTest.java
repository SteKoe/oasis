package de.stekoe.idss.page.project.criterion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.NominalScaledCriterion;


public class SelectReferenceCriterionModelTest {
    @Test
    public void selectOneCriterion() throws Exception {
        Criterion criterion = new NominalScaledCriterion();
        criterion.setName("Criterion 1");

        CriterionGroup criterionGroup = new CriterionGroup();
        criterionGroup.setName("Group 1");
        criterionGroup.getCriterions().add(criterion);

        List<CriterionGroup> selectedCriterionGroups = new ArrayList<CriterionGroup>();

        // Before insertion: the list of selected groups is emtpy
        assertThat(selectedCriterionGroups.size(), is(equalTo(0)));

        SelectReferenceCriterionModel model = new SelectReferenceCriterionModel(criterionGroup, criterion, selectedCriterionGroups);
        model.setObject(true);

        // After insertion: the list of selected groups is one and the inserted CriterionGroup contains the selected criterion
        assertThat(selectedCriterionGroups.size(), is(equalTo(1)));
        assertThat(selectedCriterionGroups.get(0).getCriterions().size(), is(equalTo(1)));
    }

    @Test
    public void deselectOneCriterion() throws Exception {
        CriterionGroup criterionGroup = new CriterionGroup();
        criterionGroup.setName("Group 1");

        Criterion criterionA = new NominalScaledCriterion();
        criterionA.setName("Criterion 1");
        criterionGroup.getCriterions().add(criterionA);

        Criterion criterionB = new NominalScaledCriterion();
        criterionB.setName("Criterion 2");
        criterionGroup.getCriterions().add(criterionB);

        Criterion criterionC = new NominalScaledCriterion();
        criterionC.setName("Criterion 3");
        criterionGroup.getCriterions().add(criterionC);


        List<CriterionGroup> selectedCriterionGroups = new ArrayList<CriterionGroup>();
        CriterionGroup selectedGroup = new CriterionGroup(criterionGroup, false);
        selectedGroup.getCriterions().add(Criterion.copyCriterion(criterionA));
        selectedGroup.getCriterions().add(Criterion.copyCriterion(criterionC));
        selectedCriterionGroups.add(selectedGroup);

        // Before deletion: We have selected one CriterionGroup which contains two selected Criterions.
        assertThat(selectedCriterionGroups.size(), is(equalTo(1)));
        assertThat(selectedCriterionGroups.get(0).getCriterions().size(), is(equalTo(2)));

        // Delete the Criterion criterionC by simulation a "deselection" of the Criterion
        SelectReferenceCriterionModel model = new SelectReferenceCriterionModel(criterionGroup, criterionC, selectedCriterionGroups);
        model.setObject(false);

        // After first deletion: We expect that there is still one selected CriterionGroup but having just one selected Criterion
        assertThat(selectedCriterionGroups.size(), is(equalTo(1)));
        assertThat(selectedCriterionGroups.get(0).getCriterions().size(), is(equalTo(1)));

        // Now we delete the last remaining Criterion from the list.
        model = new SelectReferenceCriterionModel(criterionGroup, criterionA, selectedCriterionGroups);
        model.setObject(false);

        /*
         *  We expect, that even the  CriterionGroup has been removed as we just want to have CriterionGroups copied
         *  with at least one selected Criterion
         */
        assertThat(selectedCriterionGroups.size(), is(equalTo(0)));
    }
}
