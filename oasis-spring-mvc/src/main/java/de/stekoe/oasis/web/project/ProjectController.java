package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import de.stekoe.oasis.web.JSONValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProjectController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    JSONValidator jsonValidator;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView list(Locale locale) {
        List<Project> projects = projectService.findAll();

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", messageSource.getMessage("label.project.manager", null, locale));
        model.addObject("projects", projects);
        model.addObject("projectStati", ProjectStatus.values());

        model.setViewName("project/list");
        return model;
    }

    @RequestMapping(value = "/project/{id}/edit", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).UPDATE)")
    public ModelAndView edit(@PathVariable String id) {
        Project project = projectService.findOne(id);

        ModelAndView model = new ModelAndView("project/edit");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("allStati", EvaluationStatus.values());
        return model;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).UPDATE)")
    public ModelAndView save(@PathVariable String id) {
        Project project = projectService.findOne(id);

        ModelAndView model = new ModelAndView("/project/details");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        return model;
    }


            @RequestMapping(value = "/project/{id}", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).UPDATE)")
    public String save(@ModelAttribute Project project, BindingResult bindingResult, @PathVariable String id) {
        if(bindingResult.hasErrors()) {
            return "project/edit";
        } else {
            Project p = projectService.findOne(project.getId());
            p.setName(project.getName());
            p.setDescription(project.getDescription());
            p.setProjectStatus(project.getProjectStatus());
            projectService.save(p);
            return "redirect:/project/" + id + "/edit";
        }
    }

    @RequestMapping(value = "/api/project", method = RequestMethod.POST)
    public @ResponseBody String post(@Valid @RequestBody ProjectDescriptor projectDescriptor, BindingResult bindingResult, Locale locale) {
        JSONObject jsonObject = new JSONObject();

        if(bindingResult.hasErrors()) {
            jsonObject.put("errors", jsonValidator.getErrors(bindingResult, locale));
            jsonObject.put("ok", false);
            return jsonObject.toString();
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());

            Project project = new Project(projectDescriptor);

            final ProjectRole projectRoleForCreator = createProjectRoleForCreator(project);
            final ProjectRole projectRoleForMember = createProjectRoleForMember(project);

            project.getProjectRoles().add(projectRoleForCreator);
            project.getProjectRoles().add(projectRoleForMember);

            ProjectMember projectCreator = new ProjectMember();
            projectCreator.setUser(user);
            projectCreator.setProjectRole(projectRoleForCreator);

            project.getProjectTeam().add(projectCreator);
            projectService.save(project);

            jsonObject.put("project", projectDescriptor);
            jsonObject.put("ok", true);
            return jsonObject.toString();
        }
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
