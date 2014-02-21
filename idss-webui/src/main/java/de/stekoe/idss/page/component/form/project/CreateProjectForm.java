package de.stekoe.idss.page.component.form.project;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectForm extends ProjectForm {

    @SpringBean
    private ProjectService projectService;
    @SpringBean
    private ProjectRoleService projectRoleService;

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
        projectCreator.setProjectRole(projectRoleForCreator);

        project.getProjectTeam().add(projectCreator);
        projectService.save(project);

        WebSession.get().success(getString("message.project.create.success"));
        setResponsePage(ProjectListPage.class);
    }

    /**
     * Creates a {@code ProjectRole} with {@code Permission}s for the user who created the project.
     *
     * @param project The project to create the {@code ProjectRole} for.
     * @return A new instance of {@code ProjectRole} with read/write rights on the given {@code Project}.
     */
    private ProjectRole createProjectRoleForCreator(Project project) {
        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");

        final Set<Permission> projectPermissions = new HashSet<Permission>();
        for (PermissionType permissionType : PermissionType.forProject()) {
            projectPermissions.add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }

        projectRoleCreator.setPermissions(projectPermissions);
        return projectRoleCreator;
    }

    /**
     * Creates a {@code ProjectRole} with {@code Permission}s for a default member
     * of a project.
     *
     * @param project The project to create the {@code ProjectRole} for.
     * @return A new instance of {@code ProjectRole} with read-only permissions on given {@code Project}.
     */
    private ProjectRole createProjectRoleForMember(Project project) {
        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");

        for (PermissionType permissionType : PermissionType.forReadOnly()) {
            projectRoleMember.getPermissions().add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }
        return projectRoleMember;
    }
}
