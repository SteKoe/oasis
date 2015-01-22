package de.stekoe.oasis.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.oasis.AbstractBaseTest;
import de.stekoe.oasis.TestFactory;
import de.stekoe.oasis.model.Criterion;
import de.stekoe.oasis.model.CriterionGroup;
import de.stekoe.oasis.model.CriterionPage;
import de.stekoe.oasis.model.NominalScaledCriterion;
import de.stekoe.oasis.model.NominalValue;
import de.stekoe.oasis.model.OrderableUtil.Direction;
import de.stekoe.oasis.model.Project;


public class CriterionServiceTest extends AbstractBaseTest {

    @Inject
    CriterionService criterionService;

    @Inject
    CriterionGroupService criterionGroupService;

    @Inject
    ProjectService projectService;

    @Inject
    CriterionPageService criterionPageService;

    @Test
    public void findAllForProject() throws Exception {
        Project project = TestFactory.createProject();
        projectService.save(project);

        CriterionPage criterionPage = new CriterionPage();
        criterionPage.setProject(project);

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("NSC1");
        nsc.setCriterionPage(criterionPage);
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.save(nsc);

        criterionPage.getPageElements().add(nsc);
        criterionPageService.save(criterionPage);

        criterionPage = new CriterionPage();
        criterionPage.setProject(project);
        nsc = new NominalScaledCriterion();
        nsc.setName("NSC2");
        nsc.setCriterionPage(criterionPage);
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.save(nsc);

        criterionPage.getPageElements().add(nsc);
        criterionPageService.save(criterionPage);

        List<Criterion> findAllForProject = criterionService.findAllForReport(project.getId());
        assertThat(findAllForProject.size(), equalTo(2));
    }

    @Test
    public void deleteCriterionWhichIsPartOfGroup() throws Exception {
        CriterionGroup cg = new CriterionGroup();
        cg.setName("Group");

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("Criterion");
        cg.getCriterions().add(nsc);
        nsc.setCriterionGroup(cg);

        criterionGroupService.save(cg);

        CriterionGroup findOne = criterionGroupService.findOne(cg.getId());
        assertThat(findOne, is(equalTo(cg)));
        assertThat(criterionService.findOne(nsc.getId()), is(equalTo((Criterion)nsc)));

        criterionService.delete(nsc.getId());

        assertThat(criterionService.findOne(nsc.getId()), is(equalTo(null)));
        findOne = criterionGroupService.findOne(cg.getId());
        assertThat(findOne.getCriterions().size(), is(equalTo(0)));
    }

    @Test
    public void moveUp() throws Exception {
        Project project = TestFactory.createProject();
        projectService.save(project);

        CriterionPage criterionPage = new CriterionPage();
        criterionPage.setProject(project);

        NominalScaledCriterion c1 = new NominalScaledCriterion();
        c1.setName("C1");
        c1.setCriterionPage(criterionPage);

        NominalScaledCriterion c2 = new NominalScaledCriterion();
        c2.setName("C2");
        c2.setCriterionPage(criterionPage);

        criterionPageService.save(criterionPage);

        NominalScaledCriterion c1i = (NominalScaledCriterion)criterionPage.getPageElements().get(0);
        NominalScaledCriterion c2i = (NominalScaledCriterion)criterionPage.getPageElements().get(1);
        assertThat(c1i, is(equalTo(c1)));
        assertThat(c2i, is(equalTo(c2)));

        criterionPage.move(c2, Direction.UP);
        criterionPageService.save(criterionPage);

        c1i = (NominalScaledCriterion)criterionPage.getPageElements().get(0);
        c2i = (NominalScaledCriterion)criterionPage.getPageElements().get(1);
        assertThat(c1i, is(equalTo(c2)));
        assertThat(c2i, is(equalTo(c1)));
    }
}
