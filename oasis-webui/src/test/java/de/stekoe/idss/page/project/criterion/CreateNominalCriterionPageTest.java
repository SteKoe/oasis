package de.stekoe.idss.page.project.criterion;

import javax.inject.Inject;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;

import de.stekoe.idss.AbstractWicketApplicationTester;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;


public class CreateNominalCriterionPageTest extends AbstractWicketApplicationTester {

    @Inject
    ProjectService projectService;

    @Inject
    CriterionPageService criterionPageService;

    @Inject
    UserService userService;

    @Test
    public void permissionAll() throws Exception {
        Project project = new Project();
        project.setName("Project");

        User user = TestFactory.createAuthUser();
        user.setUserStatus(UserStatus.ACTIVATED);
        user.getRoles().clear();
        user.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.ALL, project.getId()));
        userService.save(user);

        ProjectMember pm = new ProjectMember();
        pm.setUser(user);
        project.getProjectTeam().add(pm);

        projectService.save(project);

        CriterionPage page = new CriterionPage();
        page.setName("Page");
        page.setProject(project);
        criterionPageService.save(page);

        getSession().signIn(TestFactory.USER_USERNAME, TestFactory.USER_PASSWORD);
        PageParameters pageParameters = new PageParameters()
            .add("projectId", project.getId())
            .add("pageId", page.getId());

        wicketTester.startPage(CreateNominalCriterionPage.class, pageParameters);
        wicketTester.assertNoErrorMessage();

        wicketTester.assertRenderedPage(CreateNominalCriterionPage.class);
    }
}
