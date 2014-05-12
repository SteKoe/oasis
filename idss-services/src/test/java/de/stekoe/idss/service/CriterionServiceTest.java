package de.stekoe.idss.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.Project;


public class CriterionServiceTest extends AbstractBaseTest {

    @Inject
    CriterionService criterionService;

    @Inject
    ProjectService projectService;

    @Inject
    CriterionPageService criterionPageService;

    @Test
    public void testName() throws Exception {
        Project project = TestFactory.createProject();
        projectService.save(project);

        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setName("NSC1");
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.saveCriterion(nsc);

        CriterionPage criterionPage = new CriterionPage();
        criterionPage.setProject(project);
        criterionPage.getPageElements().add(nsc);
        criterionPageService.save(criterionPage);

        nsc = new NominalScaledCriterion();
        nsc.setName("NSC2");
        nsc.getValues().add(new NominalValue("A"));
        nsc.getValues().add(new NominalValue("B"));
        nsc.getValues().add(new NominalValue("C"));
        criterionService.saveCriterion(nsc);

        criterionPage = new CriterionPage();
        criterionPage.setProject(project);
        criterionPage.getPageElements().add(nsc);
        criterionPageService.save(criterionPage);

        List<Criterion> findAllForProject = criterionService.findAllForProject(project.getId());
        assertThat(findAllForProject.size(), equalTo(2));
    }
}
