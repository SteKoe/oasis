package de.stekoe.oasis.rest;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.CriterionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.List;

@RestController
public class RestCriterionController {

    @Autowired
    CriterionService criterionService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    CriterionGroupService criterionGroupService;

    @RequestMapping(value = "/api/criterion/{cid}/move/{dir}/{delta}", method = RequestMethod.PUT)
    @ResponseBody
    public String move(@PathVariable String cid, @PathVariable String dir, @PathVariable Integer delta) {
        Criterion criterion = criterionService.findOne(cid);

        if(criterion == null) {
            return Json.createObjectBuilder().add("ok", false).add("error", String.format("Could not find Criterion with id $s!", cid)).build().toString();
        }

        CriterionGroup criterionGroup = criterion.getCriterionGroup();
        CriterionPage criterionPage = criterion.getCriterionPage();

        // Criterion can have CriterionGroup as parent OR is child of a page directly
        if(criterionGroup != null) {
            criterionPage = criterionGroup.getCriterionPage();
            List<Criterion> criterions = criterionGroup.getCriterions();

            int currentIndex = criterions.indexOf(criterion);

            int otherIndex = -1;
            if(dir.equalsIgnoreCase("up")) {
                otherIndex = currentIndex - delta.intValue();
            } else if(dir.equalsIgnoreCase("down")) {
                otherIndex = currentIndex + delta.intValue();
            } else {
                return Json.createObjectBuilder().add("ok", false).add("error", String.format("Invalid direction %s!", dir)).build().toString();
            }

            Criterion otherCriterion = criterions.get(otherIndex);
            criterions.set(currentIndex, otherCriterion);
            criterions.set(otherIndex, criterion);

            criterionGroup.setCriterions(criterions);
        } else if(criterionPage != null) {
            int currentIndex = criterionPage.getPageElements().indexOf(criterion);

            int otherIndex = -1;
            if(dir.equalsIgnoreCase("up")) {
                otherIndex = currentIndex - delta.intValue();
            } else if(dir.equalsIgnoreCase("down")) {
                otherIndex = currentIndex + delta.intValue();
            } else {
                return Json.createObjectBuilder().add("ok", false).add("error", String.format("Invalid direction %s!", dir)).build().toString();
            }

            List<PageElement> pageElements = criterionPage.getPageElements();
            PageElement otherElement = pageElements.get(otherIndex);
            pageElements.set(currentIndex, otherElement);
            pageElements.set(otherIndex, criterion);
        }


        criterionPageService.save(criterionPage);

        return Json.createObjectBuilder().add("ok", true).build().toString();
    }

    @RequestMapping(value = "/api/criterion/{cid}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable String cid) {
        Criterion criterion = criterionService.findOne(cid);
        CriterionGroup criterionGroup = criterion.getCriterionGroup();
        CriterionPage criterionPage = criterion.getCriterionPage();

        if(criterionGroup != null) {
            criterionGroup.getCriterions().remove(criterion);
            criterionGroupService.save(criterionGroup);
        } else if(criterionPage != null) {
            criterionPage.getPageElements().remove(criterion);
            criterionPageService.save(criterionPage);
        }


        return Json.createObjectBuilder().add("ok", true).build().toString();
    }
}
