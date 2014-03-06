package de.stekoe.idss;

import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.SetupPage;
import de.stekoe.idss.page.admin.SystemInformationPage;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.page.auth.LogoutPage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.error.Error403Page;
import de.stekoe.idss.page.error.Error404Page;
import de.stekoe.idss.page.error.Error410Page;
import de.stekoe.idss.page.error.Error500Page;
import de.stekoe.idss.page.project.*;
import de.stekoe.idss.page.project.criterion.FormTestPage;
import de.stekoe.idss.page.project.criterion.SelectCriterionPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageDetailsPage;
import de.stekoe.idss.page.project.criterion.page.CriteriaPageListPage;
import de.stekoe.idss.page.project.role.ProjectRoleEditPage;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.page.user.EditUserProfilePage;
import de.stekoe.idss.page.user.ViewUserProfilePage;
import org.apache.wicket.settings.IApplicationSettings;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ApplicationRoutes {

    @Inject
    private WebApplication webApplication;

    public void create() {
        createPageRoutes();
        createSpecialPages();
    }

    private void createSpecialPages() {
        final IApplicationSettings getWebApplicationSettings = getWebApplication().getApplicationSettings();
        getWebApplicationSettings.setPageExpiredErrorPage(Error410Page.class);
        getWebApplicationSettings.setInternalErrorPage(Error500Page.class);
        getWebApplicationSettings.setAccessDeniedPage(Error403Page.class);
        getWebApplication().mountPage("/404", Error404Page.class);
    }

    private void createPageRoutes() {
        getWebApplication().mountPage("/", HomePage.class);
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
        getWebApplication().mountPage("/project/${projectId}/setofcriteria/page/${pageId}", CriteriaPageDetailsPage.class);
        getWebApplication().mountPage("/project/${projectId}/setofcriteria/page/${pageId}/add", SelectCriterionPage.class);

        getWebApplication().mountPage("/setup", SetupPage.class);


        // TEST
        getWebApplication().mountPage("/test", FormTestPage.class);
    }

    public WebApplication getWebApplication() {
        return webApplication;
    }
}
