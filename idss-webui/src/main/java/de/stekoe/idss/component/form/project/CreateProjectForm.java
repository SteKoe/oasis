package de.stekoe.idss.component.form.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectForm extends ProjectForm {

    @SpringBean private ProjectService projectService;
    @SpringBean private ProjectRoleService projectRoleService;

    public CreateProjectForm(String id) {
        super(id);
    }

    @Override
    public void onSave(IModel<Project> model) {
        Project project = model.getObject();

        final ProjectRole projectRoleForCreator = createProjectRoleForCreator(project);
        final ProjectRole projectRoleForMember = createProjectRoleForMember(project);

        project.getProjectRoles().add(projectRoleForCreator);
        project.getProjectRoles().add(projectRoleForMember);

        ProjectMember projectCreator = new ProjectMember();
        projectCreator.setUser(WebSession.get().getUser());
        projectCreator.getProjectRoles().add(projectRoleForCreator);

        project.getProjectTeam().add(projectCreator);
        projectService.save(project);

        WebSession.get().success(getString("message.project.create.success"));
        setResponsePage(ProjectListPage.class);
    }

    private ProjectRole createProjectRoleForCreator(Project project) {
        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");

        // Allow all crud operations
        projectRoleCreator.setPermissions(Permission.createAll(PermissionObject.PROJECT, project.getId()));

        // Allow all project specific permissions
        projectRoleCreator.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.PROJECT_EDIT_MEMBER, project.getId()));

        return projectRoleCreator;
    }

    private ProjectRole createProjectRoleForMember(Project project) {
        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");
        projectRoleMember.setPermissions(Permission.createReadOnly(PermissionObject.PROJECT, project.getId()));
        return projectRoleMember;
    }
}
