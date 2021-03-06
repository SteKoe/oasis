package de.stekoe.idss.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.NominalScaledCriterion;


public class ReferenceCriterionGroupServiceTest extends AbstractBaseTest {

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    @Inject
    ReferenceCriterionService referenceCriterionService;

    @Test
    public void delete() throws Exception {
        CriterionGroup cg = new CriterionGroup();
        cg.setReferenceType(true);
        cg.setName("Group");

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setReferenceType(true);
        nsc.setName("Criterion 1");
        cg.getCriterions().add(nsc);
        nsc.getCriterionGroups().add(cg);

        nsc = new NominalScaledCriterion();
        nsc.setReferenceType(true);
        nsc.setName("Criterion 2");
        cg.getCriterions().add(nsc);
        nsc.getCriterionGroups().add(cg);

        nsc = new NominalScaledCriterion();
        nsc.setReferenceType(true);
        nsc.setName("Criterion 3");
        cg.getCriterions().add(nsc);
        nsc.getCriterionGroups().add(cg);

        assertThat(referenceCriterionGroupService.count(), is(equalTo((long)0)));
        assertThat(referenceCriterionService.count(), is(equalTo((long)0)));

        referenceCriterionGroupService.save(cg);

        assertThat(referenceCriterionGroupService.count(), is(equalTo((long)1)));
        assertThat(referenceCriterionService.count(), is(equalTo((long)3)));

        referenceCriterionGroupService.delete(cg.getId());

        assertThat(referenceCriterionGroupService.findOne(cg.getId()), is(equalTo(null)));
        assertThat(referenceCriterionService.findAll().size(), is(equalTo(3)));
    }
}
