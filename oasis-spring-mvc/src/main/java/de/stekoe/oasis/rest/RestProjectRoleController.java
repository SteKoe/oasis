package de.stekoe.oasis.rest;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.json.Json;
import java.util.List;

@RestController
public class RestProjectRoleController {

    @Autowired
    ProjectRoleService projectRoleService;

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/api/project/{pid}/role/{rid}/move/{dir}/{delta}", method = RequestMethod.PUT)
    public String move(@PathVariable String rid, @PathVariable String dir, @PathVariable Integer delta) {
        ProjectRole projectRole = projectRoleService.findOne(rid);
        Project project = projectRole.getProject();
        List<ProjectRole> projectRoles = project.getProjectRoles();

        int currentIndex = projectRoles.indexOf(projectRole);

        int otherIndex = -1;
        if(dir.equalsIgnoreCase("up")) {
            otherIndex = currentIndex - delta.intValue();
        } else if(dir.equalsIgnoreCase("down")) {
            otherIndex = currentIndex + delta.intValue();
        }

        if(otherIndex >= 0) {
            ProjectRole otherRole = projectRoles.get(otherIndex);
            projectRoles.set(currentIndex, otherRole);
            projectRoles.set(otherIndex, projectRole);

            projectService.save(project);

            return Json.createObjectBuilder().add("ok", "true").build().toString();
        }

        return Json.createObjectBuilder().add("ok", "false").build().toString();
    }
}
