package de.stekoe.idss.service;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.Project;

public class CriterionPageServiceTest extends AbstractBaseTest {

    @Inject
    CriterionPageService criterionPageService;

    @Inject
    ProjectService projectService;

    @Test
    public void savePage() throws Exception {
        final Project project = TestFactory.createProject();
        Project save = projectService.save(project);

        final CriterionPage entity = new CriterionPage();
        entity.setProject(project);

        final String id = entity.getId();
        criterionPageService.save(entity);

        final CriterionPage byId = criterionPageService.findOne(id);
        Assert.assertNotNull(byId);
    }
}
