package de.stekoe.oasis.web.project.criterions;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.oasis.web.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectCriterionsController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    CriterionService criterionService;

    @RequestMapping(value = "/project/{pid}/criterions")
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView list(@PathVariable String pid, @RequestParam(required = false) Integer page) {
        if(page == null) {
            page = new Integer(0);
        }

        Project project = projectService.findOne(pid);

        long count = criterionPageService.countForProject(pid);

        PageRequest pageable = new PageRequest(page.intValue(), 1);
        List<CriterionPage> criterionPages = criterionPageService.findAllForProject(project.getId(), pageable);

        ModelAndView model = new ModelAndView("/project/criterions/list");
        model.addObject("project", project);
        model.addObject("count", count);
        model.addObject("pagination", new Pagination(count, 1, page.intValue()));
        model.addObject("criterionPages", criterionPages);
        model.addObject("pageTitle", project.getName());
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterions/{cpid}/select")
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView selectType(@PathVariable String pid, @PathVariable String cpid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/criterions/selectType");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("criterionPageId", cpid);
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterion/{cid}/edit", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView editCriterion(@PathVariable String pid, @PathVariable String cid) {
        Project project = projectService.findOne(pid);
        SingleScaledCriterion criterion = criterionService.findSingleScaledCriterionById(cid);

        ModelAndView model = new ModelAndView();
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);

        if(criterion instanceof NominalScaledCriterion) {
            NominalScaledCriterion nsc = (NominalScaledCriterion) criterion;
            model.setViewName("/project/criterions/nominal/edit");
            model.addObject("criterion", nsc);
            return model;
        } else if(criterion instanceof OrdinalScaledCriterion) {
            OrdinalScaledCriterion nsc = (OrdinalScaledCriterion) criterion;
            model.setViewName("/project/criterions/ordinal/edit");
            model.addObject("criterion", nsc);
            return model;
        }

        return null;
    }

    @ModelAttribute("criterion")
    public NominalScaledCriterion asd() {
        NominalScaledCriterion nsc = new NominalScaledCriterion();
        nsc.setValues(null);
        return nsc;
    }
}
