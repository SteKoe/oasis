package de.stekoe.oasis.web.project.criterions;

import de.stekoe.oasis.model.*;
import de.stekoe.oasis.service.CriterionGroupService;
import de.stekoe.oasis.service.CriterionPageService;
import de.stekoe.oasis.service.CriterionService;
import de.stekoe.oasis.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
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
public class OrdinalScaledCriterionController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    CriterionGroupService criterionGroupService;

    @Autowired
    CriterionService criterionService;

    @RequestMapping(value = "/project/{pid}/criterions/{cpid}/ordinal", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_CRITERIONS)")
    public ModelAndView createForm(@PathVariable String pid, @PathVariable String cpid) {
        Project project = projectService.findOne(pid);

        ModelAndView model = new ModelAndView("/project/criterions/ordinal/edit");
        model.addObject("project", project);
        model.addObject("criterionPageId", cpid);
        model.addObject("pageTitle", project.getName());
        model.addObject("criterion", new OrdinalScaledCriterion());
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterion/{cpid}/ordinal", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_CRITERIONS)")
    public String post(@PathVariable String pid, @PathVariable String cpid, @Valid @ModelAttribute OrdinalScaledCriterion osc, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()) {
            CriterionPage criterionPage = criterionPageService.findOne(cpid);
            CriterionGroup criterionGroup = criterionGroupService.findOne(cpid);

            if(criterionPage != null) {
                osc.setCriterionPage(criterionPage);
                criterionPage.getPageElements().add(osc);
            }
            else if(criterionGroup != null) {
                osc.setCriterionGroup(criterionGroup);
                criterionGroup.getCriterions().add(osc);
            }

            osc.getValues().forEach(value -> {
                value.setWeight(value.getWeight() == null ? 1.0 : value.getWeight());
                value.setCriterion(osc);
            });
            criterionService.save(osc);
            return String.format("redirect:/project/%s/criterions", pid, cpid);
        }
        return String.format("redirect:/project/%s/criterions/%s/ordinal", pid, cpid);
    }

    @RequestMapping(value = "/project/{pid}/criterion/ordinal/{cid}", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.oasis.model.PermissionType).MANAGE_CRITERIONS)")
    public String put(@PathVariable String pid, @PathVariable String cid, @ModelAttribute OrdinalScaledCriterion osc) {

        OrdinalScaledCriterion ssc = (OrdinalScaledCriterion) criterionService.findSingleScaledCriterionById(cid);

        List<OrdinalValue> values = new ArrayList<>();
        for(OrdinalValue value : osc.getValues()) {
            OrdinalValue ov = new OrdinalValue();
            ov.setCriterion(ssc);
            ov.setWeight(value.getWeight() == null ? 1.0 : value.getWeight());
            ov.setValue(value.getValue());
            values.add(ov);
        }

        ssc.setName(osc.getName());
        ssc.setDescription(osc.getDescription());
        ssc.setWeight(osc.getWeight());
        ssc.getValues().clear();
        ssc.getValues().addAll(values);
        ssc.setAllowNoChoice(osc.isAllowNoChoice());

        criterionService.save(ssc);

        return String.format("redirect:/project/%s/criterion/%s/edit", pid, cid);
    }

}
