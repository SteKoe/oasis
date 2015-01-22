package de.stekoe.oasis.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.oasis.AbstractBaseTest;
import de.stekoe.oasis.model.CriterionGroup;
import de.stekoe.oasis.model.NominalScaledCriterion;


public class CriterionGroupServiceTest extends AbstractBaseTest {

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    CriterionService criterionService;

    @Test
    public void delete() throws Exception {
        CriterionGroup cg = new CriterionGroup();
        cg.setName("Group");

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("Criterion 1");
        cg.getCriterions().add(nsc);
        nsc.setCriterionGroup(cg);

        nsc = new NominalScaledCriterion();
        nsc.setName("Criterion 2");
        cg.getCriterions().add(nsc);
        nsc.setCriterionGroup(cg);

        nsc = new NominalScaledCriterion();
        nsc.setName("Criterion 3");
        cg.getCriterions().add(nsc);
        nsc.setCriterionGroup(cg);

        assertThat(criterionGroupService.count(), is(equalTo((long)0)));
        assertThat(criterionService.count(), is(equalTo((long)0)));

        criterionGroupService.save(cg);

        // Before
        assertThat(criterionGroupService.count(), is(equalTo((long)1)));
        assertThat(criterionService.count(), is(equalTo((long)3)));

        criterionGroupService.delete(cg.getId());

        assertThat(criterionGroupService.findOne(cg.getId()), is(equalTo(null)));
        assertThat(criterionService.count(), is(equalTo(0L)));
    }

    @Test
    public void saveCriterionGroupWithOriginId() throws Exception {
        CriterionGroup cg = new CriterionGroup();
        cg.setName("CG");
        cg.setOriginId("12345");

        criterionGroupService.save(cg);
    }

    @Test
    public void saveCriterionGroupWhichHasBeenRetrievedFromDB() throws Exception {
        CriterionGroup cg = new CriterionGroup();
        cg.setName("CG");
        criterionGroupService.save(cg);

        assertThat(criterionGroupService.count(), equalTo(1L));

        cg = criterionGroupService.findOne(cg.getId());

        CriterionGroup criterionGroup = new CriterionGroup(cg);
        criterionGroupService.save(criterionGroup);
        assertThat(criterionGroupService.count(), equalTo(2L));
    }
}
