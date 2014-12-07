package de.stekoe.oasis.web.project.criterions;

import de.stekoe.idss.model.*;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import de.stekoe.idss.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NominalScaledCriterionController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    CriterionGroupService criterionGroupService;

    @Autowired
    CriterionService criterionService;

    @RequestMapping(value = "/project/{pid}/criterions/{cpid}/nominal", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView createForm(@PathVariable String pid, @PathVariable String cpid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/criterions/nominal/edit");
        model.addObject("project", project);
        model.addObject("parentElementId", cpid);
        model.addObject("pageTitle", project.getName());
        model.addObject("criterion", new NominalScaledCriterion());
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterion/{cpid}/nominal", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView post(@PathVariable String pid, @PathVariable String cpid, @Valid @ModelAttribute("criterion") NominalScaledCriterion nsc, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            CriterionPage criterionPage = criterionPageService.findOne(cpid);
            CriterionGroup criterionGroup = criterionGroupService.findOne(cpid);

            if(criterionPage != null) {
                nsc.setCriterionPage(criterionPage);
                criterionPage.getPageElements().add(nsc);
            }
            else if(criterionGroup != null) {
                nsc.setCriterionGroup(criterionGroup);
                criterionGroup.getCriterions().add(nsc);
            }

            nsc.getValues().forEach(value -> value.setCriterion(nsc));
            criterionService.save(nsc);
            
            return new ModelAndView(String.format("redirect:/project/%s/criterions", pid, cpid));
        }

        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/criterions/nominal/edit");
        model.addObject("project", project);
        model.addObject("parentElementId", cpid);
        model.addObject("pageTitle", project.getName());
        model.addObject("criterion", nsc);
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterion/nominal/{cid}", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public String put(@PathVariable String pid, @PathVariable String cid, @ModelAttribute NominalScaledCriterion nsc) {

        NominalScaledCriterion ssc = (NominalScaledCriterion) criterionService.findSingleScaledCriterionById(cid);

        for(NominalValue nv : nsc.getValues()) {
            nv.setCriterion(ssc);
        }

        List<NominalValue> values = new ArrayList<>();
        for(NominalValue value : nsc.getValues()) {
            NominalValue nv = new NominalValue();
            nv.setCriterion(ssc);
            nv.setValue(value.getValue());
            values.add(nv);
        }

        ssc.setName(nsc.getName());
        ssc.setDescription(nsc.getDescription());
        ssc.setWeight(nsc.getWeight());
        ssc.getValues().clear();
        ssc.getValues().addAll(values);
        ssc.setMultipleChoice(nsc.isMultipleChoice());
        ssc.setAllowNoChoice(nsc.isAllowNoChoice());

        criterionService.save(ssc);

        return String.format("redirect:/project/%s/criterion/%s/edit", pid, cid);
    }

}
