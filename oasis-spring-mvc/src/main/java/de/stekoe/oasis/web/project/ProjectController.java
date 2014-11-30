package de.stekoe.oasis.web.project;

import de.stekoe.idss.model.EvaluationStatus;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectStatus;
import de.stekoe.idss.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import sun.util.locale.LocaleExtensions;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class ProjectController {

    @Autowired
    MessageSource messageSource;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView list(Locale locale) {
        List<Project> projects = projectService.findAll();

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", messageSource.getMessage("label.project.manager", null, locale));
        model.addObject("projects", projects);
        model.setViewName("project/list");
        return model;
    }

    @RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #id, T(de.stekoe.idss.model.PermissionType).READ)")
    public ModelAndView details(@PathVariable String id) {
        Project project = projectService.findOne(id);

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.setViewName("project/details");
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
