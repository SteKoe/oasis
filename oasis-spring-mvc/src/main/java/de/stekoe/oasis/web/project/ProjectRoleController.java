package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.ProjectMemberService;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
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

    @RequestMapping(value = "/project/{pid}/role", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    public ModelAndView edit(@PathVariable String pid) {
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
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public String post(@PathVariable String pid, @RequestBody MultiValueMap<String,String> body) {
        String name = body.getFirst("name");
        List<String> permissionsTypes = body.get("permission");

        // Build Permission objects
        Set<Permission> permissions = new HashSet<>();

        if(permissionsTypes != null) {
            permissionsTypes.forEach(permissionType -> {
                    Permission permission = new Permission();
                    permission.setPermissionType(PermissionType.valueOf(permissionType));
                    permission.setPermissionObject(PermissionObject.PROJECT);
                    permission.setObjectId(pid);
                    permissions.add(permission);
            });
        }

        // Always grant READ access!
        Permission permission = new Permission();
        permission.setPermissionType(PermissionType.READ);
        permission.setPermissionObject(PermissionObject.PROJECT);
        permission.setObjectId(pid);
        permissions.add(permission);

        // Assign Permissions and Name to ProjectRole
        ProjectRole projectRole = new ProjectRole();
        projectRole.setName(name);
        projectRole.setPermissions(permissions);

        Project project = projectService.findOne(pid);
        project.getProjectRoles().add(projectRole);

        projectService.save(project);

        return "{\"ok\":true}";
    }

    @RequestMapping(value = "/api/project/{pid}/role/{rid}", method = RequestMethod.DELETE)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public String delete(@PathVariable String pid, @PathVariable String rid) {
        if(canDelete(pid,rid)) {
            Project project = projectService.findOne(pid);
            Set<ProjectRole> projectRoles = project.getProjectRoles().stream().filter(pr -> !pr.getId().equals(rid)).collect(Collectors.toSet());
            project.setProjectRoles(projectRoles);

            projectService.save(project);

            return "{\"ok\":true}";
        } else {
            return "{\"ok\":false}";
        }
    }

    @RequestMapping(value = "/api/project/{pid}/role/{rid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_ROLES)")
    @ResponseBody
    public ProjectRoleDescriptor get(@PathVariable String pid, @PathVariable String rid) {
        ProjectRole projectRole = projectRoleService.findOne(rid);
        return new ProjectRoleDescriptor(projectRole);
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
