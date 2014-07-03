package de.stekoe.idss;

import javax.inject.Inject;

import org.apache.wicket.settings.IApplicationSettings;

import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.page.LogoutPage;
import de.stekoe.idss.page.RegistrationPage;
import de.stekoe.idss.page.SetupPage;
import de.stekoe.idss.page.admin.SystemInformationPage;
import de.stekoe.idss.page.project.ProjectCreatePage;
import de.stekoe.idss.page.project.ProjectDetailsPage;
import de.stekoe.idss.page.project.ProjectEditPage;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.page.project.ProjectMemberListPage;
import de.stekoe.idss.page.project.ProjectRoleListPage;
import de.stekoe.idss.page.project.ProjectUploadDocument;
import de.stekoe.idss.page.project.criterion.SelectCriterionPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageListPage;
import de.stekoe.idss.page.project.role.ProjectRoleEditPage;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.page.user.EditUserProfilePage;
import de.stekoe.idss.page.user.ViewUserProfilePage;

public class ApplicationRoutes {

    @Inject
    private OASISWebApplication webApplication;

    public void create() {
        createPageRoutes();
        //createSpecialPages();
    }

    private void createSpecialPages() {
        final IApplicationSettings getWebApplicationSettings = getWebApplication().getApplicationSettings();
        getWebApplicationSettings.setPageExpiredErrorPage(Error410Page.class);
        getWebApplicationSettings.setInternalErrorPage(Error500Page.class);
        getWebApplicationSettings.setAccessDeniedPage(Error403Page.class);
        getWebApplication().mountPage("/404", Error404Page.class);
    }

    private void createPageRoutes() {
        getWebApplication().mountPage("/home", HomePage.class);
        getWebApplication().mountPage("/contact", ContactPage.class);

        // Login, Logout, Register, ...
        getWebApplication().mountPage("/register", RegistrationPage.class);
        getWebApplication().mountPage("/login", LoginPage.class);
        getWebApplication().mountPage("/activate", ActivateUserPage.class);
        getWebApplication().mountPage("/logout", LogoutPage.class);

        // Admin
        getWebApplication().mountPage("/admin/system", SystemInformationPage.class);

        // User Profile
        getWebApplication().mountPage("/profile/edit/#{id}", EditUserProfilePage.class);
        getWebApplication().mountPage("/profile/view/#{id}", ViewUserProfilePage.class);

        // Projects
        getWebApplication().mountPage("/project/list", ProjectListPage.class);
        getWebApplication().mountPage("/project/create", ProjectCreatePage.class);

        getWebApplication().mountPage("/project/${projectId}/show", ProjectDetailsPage.class);
        getWebApplication().mountPage("/project/${projectId}/edit", ProjectEditPage.class);
        getWebApplication().mountPage("/project/${projectId}/member", ProjectMemberListPage.class);
        getWebApplication().mountPage("/project/${projectId}/documents", ProjectUploadDocument.class);

        getWebApplication().mountPage("/project/${projectId}/roles", ProjectRoleListPage.class);
        getWebApplication().mountPage("/project/${projectId}/roles/${roleId}/edit", ProjectRoleEditPage.class);

        getWebApplication().mountPage("/project/${projectId}/setofcriteria", CriteriaPageListPage.class);
        getWebApplication().mountPage("/project/${projectId}/setofcriteria/page/${pageId}/add", SelectCriterionPage.class);

        getWebApplication().mountPage("/setup", SetupPage.class);
    }

    public OASISWebApplication getWebApplication() {
        return webApplication;
    }
}
