package de.stekoe.idss.service.impl;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.CriterionPageId;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import org.hamcrest.core.IsEqual;
import org.junit.Assert;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultCriterionPageServiceTest extends AbstractBaseTest {

    @Inject
    CriterionPageService criterionPageService;

    @Inject
    ProjectService projectService;

    @Test
    public void savePage() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        final CriterionPage entity = new CriterionPage();
        entity.setProject(project);

        final CriterionPageId id = entity.getId();
        criterionPageService.save(entity);

        final CriterionPage byId = criterionPageService.findById(id);
        Assert.assertNotNull(byId);
    }

    @Test
    public void reorderPagesAfterDelete() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        CriterionPageId pageToDelete = null;

        for (int i = 1; i < 10; i++) {
            final CriterionPage page = new CriterionPage();
            page.setProject(project);

            if (i == 3) {
                pageToDelete = page.getId();
            }

            criterionPageService.save(page);
        }

        criterionPageService.delete(pageToDelete);

        List<CriterionPage> criterionPagesForProject = criterionPageService.getCriterionPagesForProject(project.getId());
        for (int i = 0; i < criterionPagesForProject.size(); i++) {
            final CriterionPage criterionPage = criterionPagesForProject.get(i);
            assertThat(criterionPage.getOrdering(), IsEqual.equalTo(i + 1));
        }
    }

    @Test
    public void retrievedPagesAreInOrder() throws Exception {
        final Project project = TestFactory.createProject();
        projectService.save(project);

        for (int i = 1; i < 10; i++) {
            final CriterionPage page = new CriterionPage();
            page.setProject(project);
            criterionPageService.save(page);
        }

        List<CriterionPage> criterionPagesForProject = criterionPageService.getCriterionPagesForProject(project.getId());
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

        assertThat(criterionPageService.getNextPageNumForProject(project.getId()), IsEqual.equalTo(2));
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

        assertThat(page1.getOrdering(), IsEqual.equalTo(1));
        assertThat(page2.getOrdering(), IsEqual.equalTo(2));

        criterionPageService.move(page2, CriterionPageService.Direction.UP);

        page1 = criterionPageService.findById(page1.getId());
        page2 = criterionPageService.findById(page2.getId());
        assertThat(page1.getOrdering(), IsEqual.equalTo(2));
        assertThat(page2.getOrdering(), IsEqual.equalTo(1));
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

        assertThat(page1.getOrdering(), IsEqual.equalTo(1));
        assertThat(page2.getOrdering(), IsEqual.equalTo(2));

        criterionPageService.move(page1, CriterionPageService.Direction.DOWN);

        page1 = criterionPageService.findById(page1.getId());
        page2 = criterionPageService.findById(page2.getId());
        assertThat(page1.getOrdering(), IsEqual.equalTo(2));
        assertThat(page2.getOrdering(), IsEqual.equalTo(1));
    }
}
