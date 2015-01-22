package de.stekoe.oasis.rest;

import de.stekoe.oasis.model.*;
import de.stekoe.oasis.service.ProjectService;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.web.JSONValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class RestProjectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    JSONValidator jsonValidator;

    @RequestMapping(value = "/api/project/{pid}", method = RequestMethod.DELETE)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).DELETE)")
    @ResponseBody
    public String delete(@PathVariable String pid) {
        projectService.delete(pid);
        return Json.createObjectBuilder().add("ok", true).build().toString();
    }

    @RequestMapping(value = "/api/project", method = RequestMethod.POST)
    @ResponseBody
    public String post(@Valid @RequestBody ProjectDescriptor projectDescriptor, BindingResult bindingResult, Locale locale) {
        JsonObjectBuilder json = Json.createObjectBuilder();

        if(bindingResult.hasErrors()) {
            json.add("errors", jsonValidator.getErrors(bindingResult, locale));
            json.add("ok", false);
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());

            Project project = new Project(projectDescriptor);

            final ProjectRole projectRoleForCreator = createProjectRoleForCreator(project);
            projectRoleForCreator.setProject(project);

            final ProjectRole projectRoleForMember = createProjectRoleForMember(project);
            projectRoleForMember.setProject(project);

            project.getProjectRoles().add(projectRoleForCreator);
            project.getProjectRoles().add(projectRoleForMember);

            ProjectMember projectCreator = new ProjectMember();
            projectCreator.setUser(user);
            projectCreator.setProjectRole(projectRoleForCreator);

            project.getProjectTeam().add(projectCreator);
            projectService.save(project);

            json.add("ok", true);
        }

        return json.build().toString();
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

        final Set<Permission> projectPermissions = PermissionType.forProject()
                .stream()
                .map(permissionType -> new Permission(PermissionObject.PROJECT, permissionType, project.getId()))
                .collect(Collectors.toSet());

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
