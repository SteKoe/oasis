package de.stekoe.oasis.web.project.criterions;

import de.stekoe.oasis.model.CriterionGroup;
import de.stekoe.oasis.model.CriterionPage;
import de.stekoe.oasis.model.Project;
import de.stekoe.oasis.service.CriterionGroupService;
import de.stekoe.oasis.service.CriterionPageService;
import de.stekoe.oasis.service.ProjectService;
import de.stekoe.oasis.service.ReferenceCriterionGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class ReferenceCriterionController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    ReferenceCriterionGroupService referenceCriterionGroupService;

    @Autowired
    CriterionGroupService criterionGroupService;

    @RequestMapping(value = "/project/{pid}/criterions/{cpid}/select/referencecriterion", method = RequestMethod.GET)
    public ModelAndView view(@PathVariable String pid, @PathVariable String cpid) {
        Project project = projectService.findOne(pid);
        CriterionPage criterionPage = criterionPageService.findOne(cpid);

        Iterable<CriterionGroup> referenceCriterionGroups = referenceCriterionGroupService.findAll();
        ReferenceCriterionSelection attributeValue = new ReferenceCriterionSelection();

        ModelAndView model = new ModelAndView("/project/criterions/referencecriterion/select");
        model.addObject("pageTitle", project.getName());
        model.addObject("project", project);
        model.addObject("criterionPage", criterionPage);
        model.addObject("refcSel", attributeValue);
        model.addObject("referenceCriterionGroups", referenceCriterionGroups);
        return model;
    }

    @RequestMapping(value = "/project/{pid}/criterions/{cpid}/select/referencecriterion", method = RequestMethod.POST)
    public String post(@ModelAttribute ReferenceCriterionSelection referenceCriterionSelection, @PathVariable String pid, @PathVariable String cpid) {
        CriterionPage criterionPage = criterionPageService.findOne(cpid);

        Map<String, List<String>> selectedReferenceCriterions = referenceCriterionSelection.getSelectedReferenceCriterions();
        Iterator<String> criterionGroupIds = selectedReferenceCriterions.keySet().iterator();

        while(criterionGroupIds.hasNext()) {
            String criterionGroupId = criterionGroupIds.next();
            List<String> selectedCriterions = selectedReferenceCriterions.get(criterionGroupId);
            if(selectedCriterions != null) {
                CriterionGroup criterionGroup = referenceCriterionGroupService.findOne(criterionGroupId);

                CriterionGroup clone = CriterionGroup.selectiveCopy(criterionGroup, selectedCriterions);
                clone.setCriterionPage(criterionPage);
                criterionPage.getPageElements().add(clone);

                criterionGroupService.save(clone);
            }
        }

        return "redirect:/project/" + pid + "/criterions";
    }
}
