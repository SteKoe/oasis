package de.stekoe.idss.service;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.hamcrest.core.IsEqual;
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

    @Test
    public void reorderPagesAfterDelete() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        String pageToDelete = null;

        for (int i = 1; i < 10; i++) {
            final CriterionPage page = new CriterionPage();
            page.setProject(project);

            if (i == 3) {
                pageToDelete = page.getId();
            }

            criterionPageService.save(page);
        }

        criterionPageService.delete(pageToDelete);

        List<CriterionPage> criterionPagesForProject = criterionPageService.findAllForProject(project.getId());
        for (int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            assertThat(criterionPage.getOrdering(), IsEqual.equalTo(i));
        }
    }

    @Test
    public void retrievedPagesAreInOrder() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        for (int i = 1; i < 10; i++) {
            final CriterionPage page = new CriterionPage();
            page.setProject(project);
            page.setOrdering(i);
            criterionPageService.save(page);
        }

        List<CriterionPage> criterionPagesForProject = criterionPageService.findAllForProject(project.getId());
        for (int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            assertThat(criterionPage.getOrdering(), IsEqual.equalTo(i + 1));
        }
    }

    @Test
    public void getNextPageNum() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        final CriterionPage page = new CriterionPage();
        page.setProject(project);
        criterionPageService.save(page);
        assertThat(criterionPageService.findAll().size(), IsEqual.equalTo(1));

        assertThat(criterionPageService.getNextPageNumForProject(project.getId()), IsEqual.equalTo(1));
    }

    @Test
    public void movePageUp() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        CriterionPage page1 = new CriterionPage();
        page1.setProject(project);
        criterionPageService.save(page1);

        CriterionPage page2 = new CriterionPage();
        page2.setProject(project);
        criterionPageService.save(page2);

        assertThat(page1.getOrdering(), IsEqual.equalTo(0));
        assertThat(page2.getOrdering(), IsEqual.equalTo(1));

        criterionPageService.move(page2, Direction.UP);

        page1 = criterionPageService.findOne(page1.getId());
        page2 = criterionPageService.findOne(page2.getId());
        assertThat(page1.getOrdering(), IsEqual.equalTo(1));
        assertThat(page2.getOrdering(), IsEqual.equalTo(0));
    }

    @Test
    public void movePageDown() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        CriterionPage page1 = new CriterionPage();
        page1.setProject(project);
        criterionPageService.save(page1);

        CriterionPage page2 = new CriterionPage();
        page2.setProject(project);
        criterionPageService.save(page2);

        assertThat(page1.getOrdering(), IsEqual.equalTo(0));
        assertThat(page2.getOrdering(), IsEqual.equalTo(1));

        criterionPageService.move(page1, Direction.DOWN);

        page1 = criterionPageService.findOne(page1.getId());
        page2 = criterionPageService.findOne(page2.getId());
        assertThat(page1.getOrdering(), IsEqual.equalTo(1));
        assertThat(page2.getOrdering(), IsEqual.equalTo(0));
    }
}
