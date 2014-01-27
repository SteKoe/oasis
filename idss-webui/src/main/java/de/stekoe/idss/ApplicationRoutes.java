package de.stekoe.idss;

import de.stekoe.idss.page.ContactPage;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.admin.SystemInfoPage;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.page.auth.LogoutPage;
import de.stekoe.idss.page.auth.RegistrationPage;
import de.stekoe.idss.page.error.Error403Page;
import de.stekoe.idss.page.error.Error404Page;
import de.stekoe.idss.page.error.Error410Page;
import de.stekoe.idss.page.error.Error500Page;
import de.stekoe.idss.page.project.*;
import de.stekoe.idss.page.user.ActivateUserPage;
import de.stekoe.idss.page.user.EditUserProfilePage;
import de.stekoe.idss.page.user.ViewUserProfilePage;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.settings.IApplicationSettings;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ApplicationRoutes {

    @Inject private WebApplication webApplication;
    @Inject private RestRoutes restRoutes;

    public void create() {
        createPageRoutes();
        createSpecialPages();
        createRestRoutes();
    }

    private void createRestRoutes() {
        getWebApplication().mountResource("/rest", new ResourceReference("restReference") {
            @Override
            public IResource getResource() {
                return restRoutes;
            }
        });
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

        // User Profile
        getWebApplication().mountPage("/profile/edit/#{id}", EditUserProfilePage.class);
        getWebApplication().mountPage("/profile/view/#{id}", ViewUserProfilePage.class);

        // Projects
        getWebApplication().mountPage("/project/list", ProjectListPage.class);
        getWebApplication().mountPage("/project/create", ProjectCreatePage.class);
        getWebApplication().mountPage("/project/${id}/show", ProjectDetailsPage.class);
        getWebApplication().mountPage("/project/${id}/edit", ProjectEditPage.class);
        getWebApplication().mountPage("/project/${id}/member", ProjectMemberListPage.class);
        getWebApplication().mountPage("/project/${id}/roles", ProjectRoleListPage.class);
        getWebApplication().mountPage("/project/${id}/roles/${roleId}/edit", ProjectRoleEditPage.class);

        getWebApplication().mountPage("/admin/sysinfo", SystemInfoPage.class);
    }

    public WebApplication getWebApplication() {
        return webApplication;
    }
}
