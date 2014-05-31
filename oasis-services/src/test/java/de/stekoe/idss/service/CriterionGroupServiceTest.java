package de.stekoe.idss.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.NominalScaledCriterion;


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
        nsc.getCriterionGroups().add(cg);

        nsc = new NominalScaledCriterion();
        nsc.setName("Criterion 2");
        cg.getCriterions().add(nsc);
        nsc.getCriterionGroups().add(cg);

        nsc = new NominalScaledCriterion();
        nsc.setName("Criterion 3");
        cg.getCriterions().add(nsc);
        nsc.getCriterionGroups().add(cg);

        assertThat(criterionGroupService.count(), is(equalTo((long)0)));
        assertThat(criterionService.count(), is(equalTo((long)0)));

        criterionGroupService.save(cg);

        // Before
        assertThat(criterionGroupService.count(), is(equalTo((long)1)));
        assertThat(criterionService.count(), is(equalTo((long)3)));

        criterionGroupService.delete(cg.getId());

        assertThat(criterionGroupService.findOne(cg.getId()), is(equalTo(null)));
        assertThat(criterionService.findAll().size(), is(equalTo(0)));
    }
}
