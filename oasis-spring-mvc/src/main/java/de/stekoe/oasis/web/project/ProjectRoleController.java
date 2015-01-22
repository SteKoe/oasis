package de.stekoe.oasis.web.project;

import de.stekoe.oasis.model.*;
import de.stekoe.oasis.service.ProjectMemberService;
import de.stekoe.oasis.service.ProjectRoleService;
import de.stekoe.oasis.service.ProjectService;
import de.stekoe.oasis.service.UserService;
import de.stekoe.oasis.web.JSONValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProjectRoleController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectRoleService projectRoleService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectMemberService projectMemberService;

    @Autowired
    JSONValidator jsonValidator;

    @RequestMapping(value = "/project/{pid}/role", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView list(@PathVariable String pid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("project/role/list");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("permissionTypes", PermissionType.forProject());
        return model;
    }

    /*
     * ==== REST API ===================================================================================================
     */

    @RequestMapping(value = "/api/project/{pid}/role", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public String post(@PathVariable String pid, @Valid @RequestBody ProjectRoleDescriptor role, BindingResult bindingResult, Locale locale) {

        if(bindingResult.hasErrors()) {
            JsonObjectBuilder json = Json.createObjectBuilder();
            json.add("errors", jsonValidator.getErrors(bindingResult, locale));
            json.add("ok", false);
            return json.build().toString();
        }

        List<String> permissionsTypes = role.getPermissions();

        Set<Permission> permissions = role
                .getPermissions()
                .stream()
                .map(permissionType -> new Permission(PermissionObject.PROJECT, PermissionType.valueOf(permissionType), pid))
                .collect(Collectors.toSet());

        // Assign Permissions and Name to ProjectRole
        ProjectRole projectRole = new ProjectRole();
        projectRole.setName(role.getName());
        projectRole.setPermissions(permissions);

        Project project = projectService.findOne(pid);
        project.getProjectRoles().add(projectRole);

        projectService.save(project);

        JsonObject json = Json.createObjectBuilder()
                .add("ok", true)
                .build();
        return json.toString();
    }

    @RequestMapping(value = "/api/project/{pid}/role/{rid}", method = RequestMethod.DELETE)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public String delete(@PathVariable String pid, @PathVariable String rid) {
        if(canDelete(pid,rid)) {
            Project project = projectService.findOne(pid);
            List<ProjectRole> projectRoles = project.getProjectRoles().stream().filter(pr -> !pr.getId().equals(rid)).collect(Collectors.toList());
            project.setProjectRoles(projectRoles);

            projectService.save(project);

            return "{\"ok\":true}";
        } else {
            return "{\"ok\":false}";
        }
    }

    @RequestMapping(value = "/api/project/{pid}/role/{rid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public ProjectRoleDescriptor get(@PathVariable String pid, @PathVariable String rid) {
        ProjectRole projectRole = projectRoleService.findOne(rid);
        ProjectRoleDescriptor projectRoleDescriptor = new ProjectRoleDescriptor(projectRole);
        return projectRoleDescriptor;
    }

    @RequestMapping(value = "/api/project/{pid}/role/{rid}", method = RequestMethod.PUT)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public String edit(@PathVariable String pid, @PathVariable String rid, @RequestBody ProjectRoleDescriptor projectRoleDescriptor) {
        ProjectRole projectRole = projectRoleService.findOne(rid);
        Set<Permission> permissions = projectRoleDescriptor
                .getPermissions()
                .stream()
                .map(permissionType -> new Permission(PermissionObject.PROJECT, PermissionType.valueOf(permissionType), pid))
                .collect(Collectors.toSet());
        projectRole.setPermissions(permissions);

        projectRoleService.save(projectRole);

        return Json.createObjectBuilder().add("ok", true).build().toString();
    }

    /**
     * Checks if a ProjectRole can be deleted.
     * It cannot be deleted when users have the role assigned.
     *
     * @param pid Project id
     * @param rid ProjectRole id
     * @return true if ProjectRole can be deleted, false otherwise
     */
    public boolean canDelete(String pid, String rid) {
        Project project = projectService.findOne(pid);
        Set<ProjectMember> projectTeam = project.getProjectTeam();
        Long memberCount = projectTeam.stream().filter(pm -> pm.getProjectRole().getId().equals(rid)).collect(Collectors.counting());

        return memberCount == 0;
    }
}
