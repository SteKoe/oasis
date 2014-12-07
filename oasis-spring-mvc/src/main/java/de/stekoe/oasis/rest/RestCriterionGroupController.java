package de.stekoe.oasis.rest;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionGroupDescriptor;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.service.CriterionGroupService;
import de.stekoe.idss.service.CriterionPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.nio.file.Path;
import java.util.List;

@RestController
public class RestCriterionGroupController {

    @Autowired
    private CriterionGroupService criterionGroupService;

    @Autowired
    private CriterionPageService criterionPageService;

    @RequestMapping(value = "/api/criteriongroup/{cpid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public CriterionGroupDescriptor getOne(@PathVariable String cpid) {
        CriterionGroup criterionGroup = criterionGroupService.findOne(cpid);

        return new CriterionGroupDescriptor(criterionGroup);
    }

    @RequestMapping(value = "/api/criteriongroup/{cpid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public String delete(@PathVariable String cpid) {
        try {
            criterionGroupService.delete(cpid);
            return Json.createObjectBuilder().add("ok","true").build().toString();
        } catch (Exception e) {
            return Json.createObjectBuilder().add("ok","false").add("error", e.getMessage()).build().toString();
        }
    }

    @RequestMapping(value = "/api/criteriongroup/{cpid}/move/{dir}/{delta}", method = RequestMethod.PUT)
    @ResponseBody
    public String move(@PathVariable String cpid, @PathVariable String dir, @PathVariable Integer delta) {
        CriterionGroup criterionGroup = criterionGroupService.findOne(cpid);
        CriterionPage criterionPage = criterionGroup.getCriterionPage();
        List<PageElement> pageElements = criterionPage.getPageElements();
        int currentIndex = pageElements.indexOf(criterionGroup);

        int otherIndex = -1;
        if(dir.equalsIgnoreCase("up")) {
            otherIndex = currentIndex - delta.intValue();
        } else if(dir.equalsIgnoreCase("down")) {
            otherIndex = currentIndex + delta.intValue();
        } else {
            return Json.createObjectBuilder().add("ok", false).add("error", String.format("Invalid direction %s!", dir)).build().toString();
        }

        PageElement otherPageElement = pageElements.get(otherIndex);
        pageElements.set(currentIndex, otherPageElement);
        pageElements.set(otherIndex, criterionGroup);
        criterionPage.setPageElements(pageElements);

        criterionPageService.save(criterionPage);

        return Json.createObjectBuilder().add("ok", true).build().toString();
    }
}
