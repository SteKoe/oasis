package de.stekoe.oasis.web.project.criterions;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.OrderableUtil;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectCriterionGroupController {

    @Autowired
    CriterionGroupService criterionGroupService;

    @Autowired
    CriterionPageService criterionPageService;

    @RequestMapping(value = "/project/{pid}/criteriongroup/{gid}/move/{direction}")
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    public String move(@PathVariable String gid, @PathVariable String direction, RedirectAttributes redirectAttributes) {
        direction = direction.toUpperCase();

        CriterionGroup criterionGroup = criterionGroupService.findOne(gid);
        CriterionPage criterionPage = criterionGroup.getCriterionPage();

        if(OrderableUtil.Direction.UP.toString().equals(direction)) {
            criterionPage.move(criterionGroup, OrderableUtil.Direction.UP);
            redirectAttributes.addFlashAttribute("flashSuccess", "Moved up!");
        } else if(OrderableUtil.Direction.DOWN.toString().equals(direction)) {
            criterionPage.move(criterionGroup, OrderableUtil.Direction.DOWN);
            redirectAttributes.addFlashAttribute("flashSuccess", "Moved down!");
        } else {
            redirectAttributes.addFlashAttribute("flashError", "ERROR!");
        }

        criterionPageService.save(criterionPage);
        return "redirect:/project/" + criterionPage.getProject().getId() + "/criterions";
    }
}
