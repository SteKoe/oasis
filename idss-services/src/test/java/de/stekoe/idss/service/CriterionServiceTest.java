package de.stekoe.idss.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.OrderableUtil.Direction;
import de.stekoe.idss.model.Project;


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

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("NSC1");
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.save(nsc);

        CriterionPage criterionPage = new CriterionPage();
        criterionPage.setProject(project);
        criterionPage.getPageElements().add(nsc);
        criterionPageService.save(criterionPage);

        nsc = new NominalScaledCriterion();
        nsc.setName("NSC2");
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.save(nsc);

        criterionPage = new CriterionPage();
        criterionPage.setProject(project);
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
        cg.addCriterion(nsc);

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
        criterionPage.addPageElement(c1);

        NominalScaledCriterion c2 = new NominalScaledCriterion();
        c2.setName("C2");
        criterionPage.addPageElement(c2);
        criterionPageService.save(criterionPage);

        assertThat((NominalScaledCriterion)criterionPage.getPageElements().get(0), is(equalTo(c1)));
        assertThat((NominalScaledCriterion)criterionPage.getPageElements().get(1), is(equalTo(c2)));

        assertThat(criterionPage.move(c2, Direction.UP), is(true));
        criterionPageService.save(criterionPage);

        assertThat((NominalScaledCriterion)criterionPage.getPageElements().get(0), is(equalTo(c2)));
        assertThat((NominalScaledCriterion)criterionPage.getPageElements().get(1), is(equalTo(c1)));
    }
}
