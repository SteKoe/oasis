package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import de.stekoe.oasis.web.JSONValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByUsername(username);
        List<Project> projects = projectService.findByUserId(user.getId());

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", messageSource.getMessage("label.project.manager", null, locale));
        model.addObject("projects", projects);
        model.addObject("projectStati", ProjectStatus.values());

        model.setViewName("project/list");
        return model;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).UPDATE)")
    public ModelAndView show(@PathVariable String id) {
        Project project = projectService.findOne(id);

        Map<ProjectRole, List<ProjectMember>> groupedMembers = new LinkedHashMap<>();
        project.getProjectRoles().forEach(projectRole -> {
            List<ProjectMember> pm = project.getMembersByRole(projectRole);
            groupedMembers.put(projectRole, pm);
        });

        ModelAndView model = new ModelAndView("/project/details");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("groupedMembers", groupedMembers);
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
}
