package de.stekoe.idss.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;


public class CriterionGroupTest {
    @Test
    public void testCopyConstruktor() throws Exception {

        CriterionGroup criteriaGroup = new CriterionGroup();
        criteriaGroup.getCriterions().add(new NominalScaledCriterion());
        criteriaGroup.getCriterions().add(new OrdinalScaledCriterion());
        criteriaGroup.getCriterions().add(new MultiScaledCriterion());

        CriterionGroup clonedCriteriaGroup = new CriterionGroup(criteriaGroup);
        assertThat(clonedCriteriaGroup.getCriterions().size(), is(equalTo(criteriaGroup.getCriterions().size())));

        assertThat(clonedCriteriaGroup.getCriterions().get(0), is(instanceOf(NominalScaledCriterion.class)));
        assertThat(clonedCriteriaGroup.getCriterions().get(0).getId(), is(not(equalTo(criteriaGroup.getCriterions().get(0).getId()))));

        assertThat(clonedCriteriaGroup.getCriterions().get(1), is(instanceOf(OrdinalScaledCriterion.class)));
        assertThat(clonedCriteriaGroup.getCriterions().get(1).getId(), is(not(equalTo(criteriaGroup.getCriterions().get(2).getId()))));

        assertThat(clonedCriteriaGroup.getCriterions().get(2), is(instanceOf(MultiScaledCriterion.class)));
        assertThat(clonedCriteriaGroup.getCriterions().get(2).getId(), is(not(equalTo(criteriaGroup.getCriterions().get(2).getId()))));
    }

    @Test
    public void copySelectedCriterionsOnly() throws Exception {
        NominalScaledCriterion criterion1 = new NominalScaledCriterion();
        OrdinalScaledCriterion criterion2 = new OrdinalScaledCriterion();
        MultiScaledCriterion criterion3 = new MultiScaledCriterion();

        CriterionGroup criteriaGroup = new CriterionGroup();
        criteriaGroup.getCriterions().add(criterion1);
        criteriaGroup.getCriterions().add(criterion2);
        criteriaGroup.getCriterions().add(criterion3);

        assertThat(criteriaGroup.getCriterions().size(), is(equalTo(3)));

        CriterionGroup copy = CriterionGroup.selectiveCopy(criteriaGroup, Arrays.asList(criterion1.getId(), criterion3.getId()));
        assertThat(copy.getCriterions().size(), is(equalTo(2)));
    }

    @Test
    public void moveDown() throws Exception {
        final CriterionPage page = new CriterionPage();

        CriterionGroup cg1 = new CriterionGroup();
        cg1.setName("CG1");

        CriterionGroup cg2 = new CriterionGroup();
        cg2.setName("CG2");

        page.getPageElements().add(cg1);
        page.getPageElements().add(cg2);


        assertEquals(cg1, page.getPageElements().get(0));
        page.move(cg1, OrderableUtil.Direction.DOWN);
        assertEquals(cg2, page.getPageElements().get(0));
    }

    @Test
    public void moveUp() throws Exception {
        final CriterionPage page = new CriterionPage();

        CriterionGroup cg1 = new CriterionGroup();
        cg1.setName("CG1");

        CriterionGroup cg2 = new CriterionGroup();
        cg2.setName("CG2");

        page.getPageElements().add(cg1);
        page.getPageElements().add(cg2);


        assertEquals(cg2, page.getPageElements().get(1));
        page.move(cg2, OrderableUtil.Direction.UP);
        assertEquals(cg1, page.getPageElements().get(1));
    }
}
