package de.stekoe.oasis.rest;

import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.CriterionPageDescriptor;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.CriterionPageService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.oasis.beans.PermissionManager;
import de.stekoe.oasis.web.JSONValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class RestCriterionPageController {

    @Autowired
    ProjectService projectService;

    @Autowired
    CriterionPageService criterionPageService;

    @Autowired
    PermissionManager permissionManager;

    @Autowired
    JSONValidator jsonValidator;

    @RequestMapping(value = "/api/project/{pid}/criterionpage/{cpid}", method = RequestMethod.GET)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    @ResponseBody
    public String get(@PathVariable String pid, @PathVariable String cpid) {
        CriterionPage criterionPage = criterionPageService.findOne(cpid);

        JsonObjectBuilder form = Json.createObjectBuilder()
                .add("name", criterionPage.getName());

        JsonObjectBuilder json = Json.createObjectBuilder()
                .add("ok", true)
                .add("form", form);

        return json.build().toString();
    }

    @RequestMapping(value = "/api/project/{pid}/criterionpage", method = RequestMethod.POST)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    @ResponseBody
    public String post(@PathVariable String pid, @Valid @RequestBody CriterionPageDescriptor criterionPageDescriptor, BindingResult bindingResult, Locale locale) {
        JsonObjectBuilder json = Json.createObjectBuilder();
        if (bindingResult.hasErrors()) {
            json.add("errors", jsonValidator.getErrors(bindingResult, locale));
            json.add("ok", false);
        } else {
            CriterionPage criterionPage = new CriterionPage();
            criterionPage.setName(criterionPageDescriptor.getName());
            criterionPage.setProject(projectService.findOne(pid));
            criterionPageService.save(criterionPage);

            json.add("ok", true);
        }

        return json.build().toString();
    }

    @RequestMapping(value = "/api/project/{pid}/criterionpage/{cpid}", method = RequestMethod.PUT)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    @ResponseBody
    public String put(@PathVariable String pid, @PathVariable String cpid, @Valid @RequestBody CriterionPageDescriptor criterionPageDescriptor, BindingResult bindingResult, Locale locale) {
        JsonObjectBuilder json = Json.createObjectBuilder();
        if (bindingResult.hasErrors()) {
            json.add("errors", jsonValidator.getErrors(bindingResult, locale));
            json.add("ok", false);
        } else {
            CriterionPage criterionPage = criterionPageService.findOne(cpid);
            criterionPage.setName(criterionPageDescriptor.getName());
            criterionPageService.save(criterionPage);

            json.add("ok", true);
        }

        return json.build().toString();
    }

    @RequestMapping(value = "/api/project/{pid}/criterionpage/{cpid}", method = RequestMethod.DELETE)
    @PreAuthorize("@permissionManager.hasProjectPermission(principal, #pid, T(de.stekoe.idss.model.PermissionType).MANAGE_CRITERIONS)")
    @ResponseBody
    public String delete(@PathVariable String pid, @PathVariable String cpid) {
        try {
            criterionPageService.delete(cpid);
            return Json.createObjectBuilder().add("ok", true).build().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return Json.createObjectBuilder().add("false", true).add("error", e.toString()).build().toString();
        }
    }

    @RequestMapping(value = "/api/criterionpage/{cpid}/move/{dir}/{delta}", method = RequestMethod.PUT)
    @ResponseBody
    public String move(@PathVariable String cpid, @PathVariable String dir, @PathVariable Integer delta) {
        CriterionPage criterionPage = criterionPageService.findOne(cpid);
        Project project = criterionPage.getProject();
        String projectId = project.getId();

        int currentIndex = criterionPage.getOrdering();
        int otherIndex = -1;

        if(dir.equalsIgnoreCase("up")) {
            otherIndex  = currentIndex - delta.intValue();
        } else if (dir.equalsIgnoreCase("down")) {
            otherIndex = currentIndex + delta.intValue();
        } else {
            return Json.createObjectBuilder().add("ok", false).add("error", String.format("Invalid direction: %s!", dir)).build().toString();
        }

        if(otherIndex != -1) {
            CriterionPage otherCriterionPage = criterionPageService.findByProjectAndOrdering(project, otherIndex);

            criterionPage.setOrdering(otherCriterionPage.getOrdering());
            otherCriterionPage.setOrdering(currentIndex);

            criterionPageService.save(criterionPage);
            criterionPageService.save(otherCriterionPage);
        }

        return Json.createObjectBuilder().add("ok", true).build().toString();
    }
}
